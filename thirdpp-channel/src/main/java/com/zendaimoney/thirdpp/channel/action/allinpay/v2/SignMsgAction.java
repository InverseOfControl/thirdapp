package com.zendaimoney.thirdpp.channel.action.allinpay.v2;

import com.zendaimoney.thirdpp.channel.action.Action;
import com.zendaimoney.thirdpp.channel.annotations.ReqActionAnnotation;
import com.zendaimoney.thirdpp.channel.communication.Message;
import com.zendaimoney.thirdpp.channel.communication.allinpay.AllinpayCommunication;
import com.zendaimoney.thirdpp.channel.dto.req.ReqDto;
import com.zendaimoney.thirdpp.channel.dto.req.allinpay.collect.trade.CollectReq;
import com.zendaimoney.thirdpp.channel.dto.req.allinpay.sign.message.SignMsgReq;
import com.zendaimoney.thirdpp.channel.dto.resp.RespDto;
import com.zendaimoney.thirdpp.channel.dto.resp.allinpay.sign.message.SignMsgResp;
import com.zendaimoney.thirdpp.channel.entity.*;
import com.zendaimoney.thirdpp.channel.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.channel.exception.PlatformException;
import com.zendaimoney.thirdpp.channel.pub.vo.BizReqVo;
import com.zendaimoney.thirdpp.channel.pub.vo.CollectReqVo;
import com.zendaimoney.thirdpp.channel.pub.vo.SignMsgReqVo;
import com.zendaimoney.thirdpp.channel.service.*;
import com.zendaimoney.thirdpp.channel.util.*;
import com.zendaimoney.thirdpp.channel.util.allinpay.AllinpayUtil;
import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.common.enums.ChannelCategory;
import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.thirdpp.common.vo.Response;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 签约短信触发Action
 *
 * @author wulj
 */
@ReqActionAnnotation(thirdType = ThirdType.ALLINPAY2, bizType = BizType.AGREEMENT_SIGN, channelCategory = ChannelCategory.SIGN_MESSAGE)
@Component("com.zendaimoney.thirdpp.channel.action.allinpay.v2.SignMsgAction")
public class SignMsgAction extends Action {
    private static final LogPrn logger = new LogPrn(SignMsgAction.class);

    @Autowired
    private AllinpayCommunication allinpayCommunication;

    @Autowired
    private RequestService requestService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private TradeResultService tradeResultService;

    @Autowired
    private CollectInfoService collectInfoService;

    @Autowired
    private SignMessageInfoService signMessageInfoService;

    public SignMsgAction() {
        super();
    }

    /**
     * 初始化签约短信触发的数据传输对象
     * @author wulj
     */
    @Override
    protected ReqDto preProcess(BizReqVo vo) throws PlatformException {
        ReqDto dto = null;
        Request request = null;
        try {
            dto = new SignMsgReq(vo);
        } catch (Exception e) {
            logger.error("初始化签约短信对象异常", e);
            // 设置request-状态为“请求报文解析失败”
            request = new Request(vo.getChannelReqId(), Constants.MessageStatus.SEND_REQUEST_PARSE_ERROR.getCode(), ExceptionUtil.getExceptionPartyMessage(e));
            requestService.update(request);
            throw new PlatformException(PlatformErrorCode.VO_2_DTO_ERROR, ExceptionUtil.getExceptionMessage(e));
        }

        return dto;
    }

    /**
     *
     * @param bizReqVo
     * @param reqDto
     * @return
     * @throws PlatformException
     */
    @Override
    protected Message process(BizReqVo bizReqVo, ReqDto reqDto) throws PlatformException {
        Message message = null;
        Request request = null;
        MessageInfo messageInfo = null;
        // todo  更新商户号到代扣明细表
        // updateCollectMerId(reqDto);
        // 拼装报文
        message = buildMessage(reqDto, bizReqVo);
        // 准备发送报文前相关处理(修改Request状态-请求报文已发送、记录发送报文)
        try {
            // 设置request-状态为“请求报文已发送”
            request = new Request(reqDto.getChannelReqId(), Constants.MessageStatus.SEND_REQUEST.getCode());
            requestService.update(request);

            SignMsgReqVo signMsgReqVo = (SignMsgReqVo) bizReqVo;
            // 记录请求报文
            messageInfo = new MessageInfo(reqDto.getChannelReqId(), MessageInfo.MSG_TYPE_Q, message.getRequestInfo(),
                    reqDto.getThirdType().getCode(), signMsgReqVo.getTradeFlow());

            // 如果报文需要记录的话
            if (Constants.MSG_IN_STORAGE_YES.equals(ConfigUtil.getInstance().getAllinpayConfig().getMsgInStorage())) {
                messageService.insert(messageInfo); // 记录日志
            } else {
                logger.info("channelCategory:" + ChannelCategory.TRADE + ",messageInfo:" + JSONHelper.bean2json(messageInfo));
            }
        } catch (Exception e) {
            logger.error("====", e);
            throw new PlatformException(PlatformErrorCode.DB_ERROR, ExceptionUtil.getExceptionMessage(e));
        }
        // 把报文发送第三方
        try {
            message = allinpayCommunication.sendMessageByHttp(message);
        } catch (Exception e) {
            logger.error("=================", e);
            // 设置http请求发送弄个异常状态码
            message.setHttpStatusCode(Constants.CommunicationStatus.HTTP_SEND_EXCEPTION.getCode());
            return message;
        }

        return message;
    }

    /**
     * http响应处理
     */
    @Override
    protected Response respHandler(Message message, BizReqVo bizReqVo) throws PlatformException {
        SignMsgResp dto = null;
        if (Constants.CommunicationStatus.HTTP_OK_STATUS.getCode().equals(message.getHttpStatusCode())) {    // 如果http请求操作成功的話
            try {
                dto = parseMessage(message, bizReqVo);  // 解析响应报文
            } catch (PlatformException e) {
                message.setHttpStatusCode(Constants.CommunicationStatus.PARSE_MESSAGE_ERROR.getCode());  // 设置状态:响应报文解析错误
                message.setMessage(Constants.CommunicationStatus.PARSE_MESSAGE_ERROR.getDesc() + ",状态码:" + message.getHttpStatusCode());     // 初始化异常信息

                return parseMessageFailHandler(message, bizReqVo);
            }

            return communicationSuccessHandler(dto, message, bizReqVo);

        } else if (Constants.CommunicationStatus.HTTP_SEND_EXCEPTION.getCode().equals(message.getHttpStatusCode())) {     // 如果http请求发送异常
            message.setMessage(Constants.CommunicationStatus.HTTP_SEND_EXCEPTION.getDesc() + ",状态码:" + message.getHttpStatusCode());    // 初始化异常信息

            return communicationThrowExceptionHandler(message, bizReqVo);
        } else {     // 调用第三方HTTP接口返回状态码不正确
            message.setMessage(Constants.CommunicationStatus.HTTP_NOT_OK_STATUS.getDesc() + ",状态码:" + message.getHttpStatusCode());

            return communicationFailHandler(message, bizReqVo);
        }

    }

    /**
     * 构建报文
     */
    @Override
    protected Message buildMessage(ReqDto dto, BizReqVo vo) throws PlatformException {
        Message message = new Message();
        try {
            message.setUrl(ConfigUtil.getInstance().getAllinpayConfig().getUrl2());
            // 请求报文，需要加密
            message.setRequestInfo(AllinpayUtil.signMsg(dto.encode(), vo));
            // 把TPP通道reqid放到Message.提供respHandler方法中
            message.setChannelReqId(dto.getChannelReqId());
            // 设置交易流水号
            message.setTradeSn(((SignMsgReqVo)vo).getTradeFlow());
        } catch (Exception e) {
            logger.error("协议支付签约短信触发报文解析失败", e);
            // 设置request-状态为“请求报文解析失败”
            Request request = new Request(dto.getChannelReqId(), Constants.MessageStatus.SEND_REQUEST_PARSE_ERROR.getCode(), ExceptionUtil.getExceptionPartyMessage(e));
            requestService.update(request);
            throw new PlatformException(PlatformErrorCode.DTO_ENCODE_ERROR, ExceptionUtil.getExceptionMessage(e));
        }

        return message;
    }

    /**
     * 返回报文转化成数据传递对象
     */
    @Override
    protected SignMsgResp parseMessage(Message message, BizReqVo bizReqVo)
            throws PlatformException {
        Request request = null;
        SignMsgResp dto = null;
        try {
            dto = new SignMsgResp();
            dto = dto.decode(message.getResponseInfo());
            // 把业务系统编码设置到返回dto中
            dto.setBizSys(bizReqVo.getBizSys());
            // 把业务类型设置到返回dto中
            dto.setBizType(bizReqVo.getBizType());
            // 把第三方系统编码设置到返回dto中
            dto.setThirdType(bizReqVo.getThirdType());
            // 把通道操作流水号设置到返回dto中
            dto.setChannelReqId(bizReqVo.getChannelReqId());
        } catch (Exception e) {
            logger.error("====", e);
            // 设置request-状态为“响应报文解析失败”
            request = new Request(message.getChannelReqId(), Constants.MessageStatus.RESPONSE_PARSE_ERROR.getCode(), ExceptionUtil.getExceptionPartyMessage(e));
            requestService.update(request);
            throw new PlatformException(PlatformErrorCode.DTO_DECODE_ERROR,
                    ExceptionUtil.getExceptionMessage(e));
        }
        return dto;
    }

    /**
     * Http请求失败处理(http返回状态码不为150)
     * 此方法不能向外抛出异常，方法内部发生异常必须在方法内部捕获并以repsonse返回
     * @param message
     * @param bizReqVo
     * @return
     * @throws PlatformException
     */
    @Override
    protected Response communicationFailHandler(Message message, BizReqVo bizReqVo) {
        SignMsgReqVo signMsgReqVo = (SignMsgReqVo) bizReqVo;
        // 返回调用方为交易处理中，对于这种状态来说需要TPP下一次明确告知对方(通过推送状态方式)该交易是否成功
        Response response = new Response(Constants.TppConstants.TRADE_STATE_FAILER.getCode(), message.getMessage());
        // 设置操作流水号
        response.setFlowId(signMsgReqVo.getReqId());
        Request request = null;

        // 发送报文后相关处理(修改Request状态-请求报文发送失败)
        try {
            // 设置request-状态为“请求报文发送失败”
            request = new Request(message.getChannelReqId(), Constants.MessageStatus.SEND_REQUEST_ERROR.getCode(), message.getMessage());
            requestService.update(request);

            // 更新短信触发记录
            SignMessageInfo signMessageInfo = signMessageInfoService.getByReqId((signMsgReqVo.getReqId()));
            signMessageInfo.setStatus(Constants.TppConstants.TRADE_STATE_FAILER.getCode());
            signMessageInfo.setFailReason(message.getMessage());
            signMessageInfoService.saveOrUpdateSignInfo(signMessageInfo);
        } catch (Exception e) {
            logger.error("==============", e);

        }
        // todo 写MONGODB-该状态需要通过查询才能确定
        /*
        try {
            CollectReqVo collectReqVo = (CollectReqVo) bizReqVo;
            collectInfoService.sendNotifyQueryMsg(collectReqVo);
        } catch (Exception e) {
            logger.error("==============", e);
        }
        */

        return response;
    }

    /**
     * Http请求成功处理(http返回状态码为150)(此方法不能向外抛出异常，方法内部发生异常必须在方法内部捕获并以repsonse返回)
     *
     * @param dto
     * @return
     * @throws PlatformException
     */
    @Override
    protected Response communicationSuccessHandler(RespDto dto, Message message, BizReqVo bizReqVo) {
        Request request = null;
        MessageInfo messageInfo = null;

        SignMsgReqVo signMsgReqVo = (SignMsgReqVo) bizReqVo;
        SignMsgResp res = (SignMsgResp) dto;

        // 交易成功
        Response response = new Response(Constants.TppConstants.TRADE_STATE_SUCCESS.getCode(), message.getMessage());
        response.setFlowId(signMsgReqVo.getReqId());        // trade模块的flowId
        response.setBankCode(signMsgReqVo.getBankCode());
        response.setBankName(signMsgReqVo.getBankName());

        // 发送报文后相关处理(修改Request状态-响应报文已收到、记录响应报文)
        try {
            // 设置request-状态为“收到响应报文”
            request = new Request(message.getChannelReqId(), Constants.MessageStatus.RECEIVED_RESPONSE.getCode());
            requestService.update(request);


            // 记录响应报文
            messageInfo = new MessageInfo(message.getChannelReqId(), MessageInfo.MSG_TYPE_S, message.getResponseInfo(),
                    dto.getThirdType().getCode(), signMsgReqVo.getTradeFlow());
            // 如果报文需要记录的话
            if (Constants.MSG_IN_STORAGE_YES.equals(ConfigUtil.getInstance().getAllinpayConfig().getMsgInStorage())) {
                // 设置交易流水号
                messageInfo.setSpare1(signMsgReqVo.getTradeFlow());
                messageService.insert(messageInfo);
            } else {
                logger.info("channelCategory:" + ChannelCategory.TRADE + ",messageInfo:" + JSONHelper.bean2json(messageInfo));
            }

        } catch (Exception e) {
            logger.error("==============", e);

        }

        // 业务逻辑处理
        // 获取所有中间态
        // String[] middleStatus = Constants.AllinpayConstants.TRADE_STATE_MID.getCode().split(",");
        // 待更新
        SignMessageInfo signMessageInfo = null;
        // 交易结果
        TradeResult tradeResult = null;
        try {
            signMessageInfo = signMessageInfoService.getByTradeFlow(message.getTradeSn());
            // 通道返回信息
            // 通道返回流水号
            String payTransFlow = "";
            if (res.getBody() != null) { // 如果响应body不为空
                // 获取响应body的错误码和错误描述
                String bodyRetCode = res.getBody().getRetCode();
                String bodyMessage = "";
                if(StringUtils.isNotEmpty(res.getBody().getErrMsg())){
                    bodyMessage = StringUtils.substring(res.getBody().getErrMsg(), 0, 150);
                }

                // 如果交易成功
               if (Constants.AllinpayConstants.TRADE_STATE_SUCC.getCode().equals(bodyRetCode)) {  // 如果交易通道返回成功(info和body中状态码都成功才算成功)
                    // 返回状态为-交易成功
                    response.setCode(Constants.TppConstants.TRADE_STATE_SUCCESS.getCode());
                    response.setMsg(bodyMessage);
                    // 交易记录状态
                    signMessageInfo.setTradeFlow(message.getTradeSn());
                    signMessageInfo.setStatus(Constants.TppConstants.TRADE_STATE_SUCCESS.getCode());     // 交易记录状态
                    signMessageInfo.setFailReason(bodyMessage);
                   // 初始化交易结果
                    tradeResult = new TradeResult(res.getThirdType().getCode(), message.getTradeSn(), payTransFlow, res.getBizType().getCode(), bodyRetCode, bodyMessage,
                            Constants.TppConstants.TRADE_STATE_SUCCESS.getCode(), message.getChannelReqId());
                    // 得到最终结果处理
                    finalResultHandler(tradeResult, signMessageInfo, response);
                } else {     // 其他定义交易失败(header和body中都有返回码，只有当两个返回码都成功时，才认为是成功的，当有一个失败时一个成功，取失败的，当两个都失败错误码，随便取一个)
                    // 返回状态为-交易失败
                    response.setCode(Constants.TppConstants.TRADE_STATE_FAILER.getCode());
                    response.setMsg(bodyMessage);
                    // 交易记录状态
                    signMessageInfo.setTradeFlow(message.getTradeSn());
                    signMessageInfo.setStatus(Constants.TppConstants.TRADE_STATE_FAILER.getCode());
                    signMessageInfo.setFailReason(bodyMessage);
                    // 初始化交易结果
                    tradeResult = new TradeResult(res.getThirdType().getCode(), message.getTradeSn(), payTransFlow, res.getBizType().getCode(), bodyRetCode, bodyMessage,
                            Constants.TppConstants.TRADE_STATE_FAILER.getCode(), message.getChannelReqId());
                    // 得到最终结果处理
                    finalResultHandler(tradeResult, signMessageInfo, response);
                }
            } else {   // 如果响应body为空
                // 获取响应body的错误码和错误描述
                String headerRetCode = res.getHeader().getRetCode();
                String headerMessage = "";
                if(StringUtils.isNotEmpty(res.getHeader().getErrMsg())){
                    headerMessage = StringUtils.substring(res.getHeader().getErrMsg(), 0, 150);
                }
                // 返回状态为-交易失败
                response.setCode(Constants.TppConstants.TRADE_STATE_FAILER.getCode());
                response.setMsg(headerMessage);
                // 交易记录状态
                signMessageInfo.setTradeFlow(message.getTradeSn());
                signMessageInfo.setStatus(Constants.TppConstants.TRADE_STATE_FAILER.getCode());
                signMessageInfo.setFailReason(headerMessage);

                // 初始化交易结果
                tradeResult = new TradeResult(res.getThirdType().getCode(), message.getTradeSn(), payTransFlow, res.getBizType().getCode(), headerRetCode, headerMessage,
                        Constants.TppConstants.TRADE_STATE_FAILER.getCode(), message.getChannelReqId());
                // 得到最终结果处理
                finalResultHandler(tradeResult, signMessageInfo, response);
            }
        } catch (Exception e) {
            logger.error("==============", e);
            // todo 写MONGODB-该状态需要通过查询才能确定
            /*
            try {
                CollectReqVo collectReqVo = (CollecReqVo) bizReqVo;
                collectInfoService.sendNotifyQueryMsg(collectReqVo);
            } catch (Exception e1) {
                logger.error("==============", e1);
            }
             */
        }

        return response;
    }

    /**
     * 响应报文解析失败处理(此方法不能向外抛出异常，方法内部发生异常必须在方法内部捕获并以repsonse返回)
     *
     * @param message
     * @return
     */
    @Override
    protected Response parseMessageFailHandler(Message message, BizReqVo bizReqVo) {
        // 返回调用方为交易处理中，对于这种状态来说需要TPP下一次明确告知对方(通过推送状态方式)该交易是否成功
        Response response = new Response(
                Constants.TppConstants.TRADE_STATE_MIDDLE.getCode(),
                message.getMessage());
        // 设置操作流水号
        response.setFlowId(message.getChannelReqId());
        // 写MONGODB-该状态需要通过查询才能确定
        try {
            CollectReqVo collectReqVo = (CollectReqVo) bizReqVo;
            collectInfoService.sendNotifyQueryMsg(collectReqVo);
        } catch (Exception e) {
            logger.error("==============", e);
        }
        return response;

    }

    /**
     * communication提交抛出异常处理(此方法不能向外抛出异常，方法内部发生异常必须在方法内部捕获并以repsonse返回)
     *
     * @param message
     * @return
     */
    @Override
    protected Response communicationThrowExceptionHandler(Message message, BizReqVo bizReqVo) {
        SignMsgReqVo signMsgReqVo = (SignMsgReqVo) bizReqVo;
        // 返回调用方为交易处理中，对于这种状态来说需要TPP下一次明确告知对方(通过推送状态方式)该交易是否成功
        Response response = new Response(Constants.TppConstants.TRADE_STATE_FAILER.getCode(), message.getMessage());
        // 设置操作流水号
        response.setFlowId(signMsgReqVo.getReqId());
        Request request = null;

        // 发送报文后相关处理(修改Request状态-请求报文发送失败)
        try {
            // 设置request-状态为“请求报文发送失败”
            request = new Request(message.getChannelReqId(), Constants.MessageStatus.SEND_REQUEST_ERROR.getCode(), message.getMessage());
            requestService.update(request);
            // 更新短信触发记录
            SignMessageInfo signMessageInfo = signMessageInfoService.getByReqId((signMsgReqVo.getReqId()));
            signMessageInfo.setStatus(Constants.TppConstants.TRADE_STATE_FAILER.getCode());
            signMessageInfo.setFailReason(message.getMessage());
            signMessageInfoService.saveOrUpdateSignInfo(signMessageInfo);

        } catch (Exception e) {
            logger.error("==============", e);
        }

        // todo 写MONGODB-该状态需要通过查询才能确定
        /*
        try {
            CollectReqVo collectReqVo = (CollectReqVo) bizReqVo;
            collectInfoService.sendNotifyQueryMsg(collectReqVo);
        } catch (Exception e) {
            logger.error("==============", e);
        }
        */

        return response;
    }

    /**
     * 交易最终结果处理
     * @param tradeResult
     * @param signMessageInfo
     * @param response
     */
    private void finalResultHandler(TradeResult tradeResult, SignMessageInfo signMessageInfo, Response response) {
        try {
            tradeResultService.finalResultHandler(tradeResult, signMessageInfo);
        } catch (Exception e) {
            // 如果本次失败的话，返回业务端状态为-交易处理中，并同时写入队列
            // 返回状态为-交易中间态
            response.setCode(Constants.TppConstants.TRADE_STATE_FAILER.getCode());
            // 如果最终结果记录失败的话，则写入MONGODB通过查询最终更新状态
            try {
                // todo 不需要发送mq通知消息
                //SignMsgReqVo signMsgReqVo = (SignMsgReqVo) bizReqVo;
                //collectInfoService.sendNotifyQueryMsg(collectReqVo);
            } catch (Exception e1) {
                logger.error("==============", e1);
            }
            logger.error("交易最终结果处理异常", e);
        }
    }

    /**
     * 中间结果处理
     *
     * @param tradeResult
     * @param message
     * @param bizReqVo
     */
    private void midResultHandler(TradeResult tradeResult, Message message, BizReqVo bizReqVo) {
        try {
            tradeResultService.insert(tradeResult);
        } catch (Exception e) {
            logger.error("交易中间结果处理异常", e);
        }
        // todo 写MONGODB-该状态需要通过查询才能确定
        /*
        try {
            CollectReqVo collectReqVo = (CollectReqVo) bizReqVo;
            collectInfoService.sendNotifyQueryMsg(collectReqVo);
        } catch (Exception e) {
            logger.error("==============", e);
        }
        */
    }

    private void updateCollectMerId(ReqDto dto) {
        CollectReq req = (CollectReq) dto;
        CollectInfo collectInfo = new CollectInfo(req.getHeader().getReqSn(), req.getBody().getMerchantId());
        try {
            tradeResultService.finalResultHandler(null, collectInfo);
        } catch (Exception e) {
            logger.error("更新代扣商户号信息异常:", e);
            throw new PlatformException(PlatformErrorCode.DB_ERROR,
                    ExceptionUtil.getExceptionMessage(e));

        }
    }


}
