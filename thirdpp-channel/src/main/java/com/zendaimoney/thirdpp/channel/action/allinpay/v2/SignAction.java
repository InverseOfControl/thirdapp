package com.zendaimoney.thirdpp.channel.action.allinpay.v2;

import com.zendaimoney.thirdpp.channel.action.Action;
import com.zendaimoney.thirdpp.channel.annotations.ReqActionAnnotation;
import com.zendaimoney.thirdpp.channel.communication.Message;
import com.zendaimoney.thirdpp.channel.communication.allinpay.AllinpayCommunication;
import com.zendaimoney.thirdpp.channel.dto.req.ReqDto;
import com.zendaimoney.thirdpp.channel.dto.req.allinpay.collect.trade.CollectReq;
import com.zendaimoney.thirdpp.channel.dto.req.allinpay.sign.SignReq;
import com.zendaimoney.thirdpp.channel.dto.resp.RespDto;
import com.zendaimoney.thirdpp.channel.dto.resp.allinpay.sign.SignResp;
import com.zendaimoney.thirdpp.channel.entity.*;
import com.zendaimoney.thirdpp.channel.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.channel.exception.PlatformException;
import com.zendaimoney.thirdpp.channel.pub.vo.BizReqVo;
import com.zendaimoney.thirdpp.channel.pub.vo.CollectReqVo;
import com.zendaimoney.thirdpp.channel.pub.vo.SignReqVo;
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
import org.springframework.transaction.annotation.Transactional;

/**
 * 签约Action
 *
 * @author wulj
 */
@ReqActionAnnotation(thirdType = ThirdType.ALLINPAY2, bizType = BizType.AGREEMENT_SIGN, channelCategory = ChannelCategory.SIGN)
@Component("com.zendaimoney.thirdpp.channel.action.allinpay.v2.SignAction")
@Transactional
public class SignAction extends Action {

    private static final LogPrn logger = new LogPrn(SignAction.class);

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
    private AccountAgreementService accountAgreementService;

    @Autowired
    private SignMessageInfoService signMessageInfoService;

    @Autowired
    private SignInfoService signInfoService;

    public SignAction() {
        super();
    }

    /**
     * 初始化签约短信触发的数据传输对象
     * @author wulj
     */
    @Override
    protected ReqDto preProcess(BizReqVo vo) throws PlatformException {
        SignReq dto = null;
        Request request = null;
        try {
            dto = new SignReq(vo);
            SignReqVo signReqVo = (SignReqVo)vo;
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

            SignReqVo signReqVo = (SignReqVo) bizReqVo;
            // 记录请求报文
            messageInfo = new MessageInfo(reqDto.getChannelReqId(), MessageInfo.MSG_TYPE_Q, message.getRequestInfo(),
                    reqDto.getThirdType().getCode(), signReqVo.getTradeFlow());

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
        SignResp dto = null;
        if (Constants.CommunicationStatus.HTTP_OK_STATUS.getCode().equals(message.getHttpStatusCode())) {    // 如果http請求操作成功
            // 如果是报文解析异常
            try {
                dto = parseMessage(message, bizReqVo);
            } catch (PlatformException e) {
                // 设置状态:响应报文解析错误
                message.setHttpStatusCode(Constants.CommunicationStatus.PARSE_MESSAGE_ERROR.getCode());
                // 初始化异常信息
                message.setMessage(Constants.CommunicationStatus.PARSE_MESSAGE_ERROR.getDesc() + ",状态码:" + message.getHttpStatusCode());
                return parseMessageFailHandler(message, bizReqVo);
            }

            return communicationSuccessHandler(dto, message, bizReqVo);
        } else if (Constants.CommunicationStatus.HTTP_SEND_EXCEPTION.getCode().equals(message.getHttpStatusCode())) {   // 如果http请求异常
            message.setMessage(Constants.CommunicationStatus.HTTP_SEND_EXCEPTION.getDesc() + ",状态码:" + message.getHttpStatusCode());

            return communicationThrowExceptionHandler(message, bizReqVo);
        } else {     // 如果http请求失败
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
            message.setTradeSn(((SignReqVo)vo).getTradeFlow());
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
    protected SignResp parseMessage(Message message, BizReqVo bizReqVo)
            throws PlatformException {
        Request request = null;
        SignResp dto = null;
        try {
            dto = new SignResp();
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
            throw new PlatformException(PlatformErrorCode.DTO_DECODE_ERROR, ExceptionUtil.getExceptionMessage(e));
        }

        return dto;
    }

    /**
     * Http请求失败处理(http返回状态码不为150)
     * 此方法不能向外抛出异常，方法内部发生异常必须在方法内部捕获并以repsonse返回
     * @param message
     * @return
     * @throws PlatformException
     */
    @Override
    protected Response communicationFailHandler(Message message, BizReqVo bizReqVo) {
        SignReqVo signReqVo = (SignReqVo) bizReqVo;
        // 返回调用方为交易处理中，对于这种状态来说需要TPP下一次明确告知对方(通过推送状态方式)该交易是否成功
        Response response = new Response(Constants.TppConstants.TRADE_STATE_FAILER.getCode(), message.getMessage());
        // 设置操作流水号
        response.setFlowId(message.getChannelReqId());
        Request request = null;

        // 发送报文后相关处理(修改Request状态-请求报文发送失败)
        try {
            // 设置request-状态为“请求报文发送失败”
            request = new Request(message.getChannelReqId(), Constants.MessageStatus.SEND_REQUEST_ERROR.getCode(), message.getMessage());
            requestService.update(request);

            // 更新签约记录
            SignInfo signInfo = signInfoService.getByReqId((signReqVo.getReqId()));
            signInfo.setStatus(Constants.TppConstants.TRADE_STATE_FAILER.getCode());
            signInfo.setFailReason(message.getMessage());
            signInfoService.saveOrUpdateSignInfo(signInfo);
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
     * http请求成功处理
     * http返回状态码为150)(此方法不能向外抛出异常，方法内部发生异常必须在方法内部捕获并以repsonse返回
     * @param dto
     * @param message
     * @param bizReqVo
     * @return
     * @throws PlatformException
     */
    @Override
    protected Response communicationSuccessHandler(RespDto dto, Message message, BizReqVo bizReqVo) {
        // 交易成功
        Response response = new Response(Constants.TppConstants.TRADE_STATE_SUCCESS.getCode(), message.getMessage());

        SignReqVo signReqVo = (SignReqVo) bizReqVo;
        SignResp res = (SignResp) dto;
        Request request = null;
        MessageInfo messageInfo = null;

        // 交易流水号
        response.setFlowId(signReqVo.getReqId());

        // 发送报文后相关处理(修改Request状态-响应报文已收到、记录响应报文)
        try {
            // 设置request-状态为“收到响应报文”
            request = new Request(message.getChannelReqId(), Constants.MessageStatus.RECEIVED_RESPONSE.getCode());
            requestService.update(request);

            // 记录响应报文
            messageInfo = new MessageInfo(message.getChannelReqId(), MessageInfo.MSG_TYPE_S, message.getResponseInfo(),
                    dto.getThirdType().getCode(), signReqVo.getTradeFlow());
            // 如果报文需要记录的话
            if (Constants.MSG_IN_STORAGE_YES.equals(ConfigUtil.getInstance().getAllinpayConfig().getMsgInStorage())) {
                // 设置交易流水号
                messageInfo.setSpare1(signReqVo.getTradeFlow());
                messageService.insert(messageInfo);
            } else {
                logger.info("channelCategory:" + ChannelCategory.TRADE + ",messageInfo:" + JSONHelper.bean2json(messageInfo));
            }

        } catch (Exception e) {
            logger.error("==============", e);

        }

        // 业务逻辑处理(获取所有中间态)
        // String[] middleStatus = Constants.AllinpayConstants.TRADE_STATE_MID.getCode().split(",");
        // 待更新
        SignInfo signInfo = null;
        // 交易结果
        TradeResult tradeResult = null;

        try {
            signInfo = signInfoService.getByReqId(signReqVo.getReqId());
            // 通道返回流水号
            String payTransFlow = "";
            if (res.getBody() != null) {
                // 获取响应body的错误码和错误描述
                String bodyRetCode = res.getBody().getRetCode();
                String bodyMessage = "";
                if(StringUtils.isNotEmpty(res.getBody().getErrMsg())){
                    bodyMessage = StringUtils.substring(res.getBody().getErrMsg(), 0, 150);
                }

                // 如果交易返回处于中间态.
                if (Constants.AllinpayConstants.TRADE_STATE_SUCC.getCode().equals(bodyRetCode)) {  // 如果交易通道返回成功(info和body中状态码都成功才算成功)
                    // 返回状态为-交易成功
                    SignMessageInfo signMessageInfo = signMessageInfoService.getByTradeFlow(signReqVo.getSrcReqSn());
                    AccountAgreement account = accountAgreementService.getAccountByNameAndIdNum(signMessageInfo.getAccountName(), signMessageInfo.getIdNum());
                    if(account == null){
                        // 新建协议支付签约账户信息
                        account = new AccountAgreement();
                        account.setName(signMessageInfo.getAccountName());
                        account.setIdNum(signMessageInfo.getIdNum());
                        account.setIdType(signMessageInfo.getIdType());
                        accountAgreementService.saveOrUpdateAccountAgreement(account);

                        account = accountAgreementService.getAccountByNameAndIdNum(signMessageInfo.getAccountName(), signMessageInfo.getIdNum());

                        AccountAgreementCard accountAgreementCard = new AccountAgreementCard(signMessageInfo, account.getId(), res.getBody().getAgrmNo());
                        accountAgreementService.saveOrUpdateAccountAgreementCard(accountAgreementCard);
                    }else{
                        AccountAgreementCard card = accountAgreementService.getCardByAccountIdAndAccountNoAndThirdType(account.getId(),
                                signMessageInfo.getAccountNo(), signMessageInfo.getPaySysNo());
                        if(card == null){
                            // 新建签约银行卡信息
                            card = new AccountAgreementCard(signMessageInfo, account.getId(), res.getBody().getAgrmNo());
                        }else{
                            // 更新签约银行卡信息
                            card.setAccountType(signMessageInfo.getAccountType());
                            card.setAccountProp(signMessageInfo.getAccountProp());
                            card.setAccountName(signMessageInfo.getAccountName());
                            card.setTel(signMessageInfo.getTel());
                            card.setCvv2(signMessageInfo.getCvv2());
                            card.setValidDate(signMessageInfo.getValidDate());
                            card.setMerrem(signMessageInfo.getMerrem());
                            card.setRemark(signMessageInfo.getRemark());
                            card.setBankCode(signMessageInfo.getBankCode());
                            card.setBankName(signMessageInfo.getBankName());
                            card.setAgrmNo(res.getBody().getAgrmNo());
                        }
                        accountAgreementService.saveOrUpdateAccountAgreementCard(card);
                    }

                    // 返回状态为-交易成功
                    response.setCode(Constants.TppConstants.TRADE_STATE_SUCCESS.getCode());
                    response.setMsg(bodyMessage);
                    response.setAgrmNo(res.getBody().getAgrmNo());   // 协议号

                    // 交易记录状态
                    signInfo = new SignInfo();
                    signInfo.setTradeFlow(message.getTradeSn());
                    signInfo.setStatus(Constants.TppConstants.TRADE_STATE_SUCCESS.getCode());
                    signInfo.setFailReason(bodyMessage);
                    signInfo.setAgrmNo(res.getBody().getAgrmNo());    // 协议号

                    // 初始化交易结果
                    tradeResult = new TradeResult(res.getThirdType().getCode(), message.getTradeSn(),
                            payTransFlow, res.getBizType().getCode(), bodyRetCode, bodyMessage,
                            Constants.TppConstants.TRADE_STATE_SUCCESS.getCode(),
                            message.getChannelReqId());
                    // 得到最终结果处理
                    finalResultHandler(tradeResult, signInfo, response);
                } else {
                    // 返回状态为-交易失败
                    response.setCode(Constants.TppConstants.TRADE_STATE_FAILER.getCode());
                    response.setMsg(bodyMessage);
                    // 交易记录状态
                    signInfo.setTradeFlow(message.getTradeSn());
                    signInfo.setStatus(Constants.TppConstants.TRADE_STATE_FAILER.getCode());
                    signInfo.setFailReason(bodyMessage);
                    // 初始化交易结果
                    tradeResult = new TradeResult(res.getThirdType().getCode(), message.getTradeSn(), payTransFlow, res.getBizType().getCode(),
                            bodyRetCode, bodyMessage,
                            Constants.TppConstants.TRADE_STATE_FAILER.getCode(), message.getChannelReqId());
                    // 得到最终结果处理
                    finalResultHandler(tradeResult, signInfo, response);
                }
            } else {
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
                signInfo.setTradeFlow(message.getTradeSn());
                signInfo.setStatus(Constants.TppConstants.TRADE_STATE_FAILER.getCode());
                signInfo.setFailReason(headerMessage);

                // 初始化交易结果
                tradeResult = new TradeResult(res.getThirdType().getCode(), message.getTradeSn(), payTransFlow, res.getBizType().getCode(), headerRetCode, headerMessage,
                        Constants.TppConstants.TRADE_STATE_FAILER.getCode(), message.getChannelReqId());
                // 得到最终结果处理
                finalResultHandler(tradeResult, signInfo, response);
            }
        } catch (Exception e) {
            logger.error("==============", e);
            // 写MONGODB-该状态需要通过查询才能确定
            /*
            try {
                signReqVo = (SignReqVo) bizReqVo;
                collectInfoService.sendNotifyQueryMsg(signReqVo);
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
    protected Response parseMessageFailHandler(Message message,
                                               BizReqVo bizReqVo) {
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
     * communication提交抛出异常处理
     * 此方法不能向外抛出异常，方法内部发生异常必须在方法内部捕获并以repsonse返回
     * @param message
     * @return
     */
    @Override
    protected Response communicationThrowExceptionHandler(Message message, BizReqVo bizReqVo) {
        SignReqVo signReqVo = (SignReqVo) bizReqVo;
        // 返回调用方为交易处理中，对于这种状态来说需要TPP下一次明确告知对方(通过推送状态方式)该交易是否成功
        Response response = new Response(Constants.TppConstants.TRADE_STATE_FAILER.getCode(), message.getMessage());
        // 设置操作流水号
        response.setFlowId(message.getChannelReqId());
        Request request = null;

        // 发送报文后相关处理(修改Request状态-请求报文发送失败)
        try {
            // 设置request-状态为“请求报文发送失败”
            request = new Request(message.getChannelReqId(), Constants.MessageStatus.SEND_REQUEST_ERROR.getCode(), message.getMessage());
            requestService.update(request);

            // 更新签约记录
            SignInfo signInfo = signInfoService.getByReqId((signReqVo.getReqId()));
            signInfo.setStatus(Constants.TppConstants.TRADE_STATE_FAILER.getCode());
            signInfo.setFailReason(message.getMessage());
            signInfoService.saveOrUpdateSignInfo(signInfo);
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
     * @param signInfo
     * @param response
     */
    private void finalResultHandler(TradeResult tradeResult, SignInfo signInfo, Response response) {
        try {
            tradeResultService.finalResultHandler(tradeResult, signInfo);
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
