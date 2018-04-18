<%@ page language="java" pageEncoding="utf-8"%>
<div class="content-body">
	<div class="search-panel-content">
		<form id="excelImp" method="post" enctype="multipart/form-data" 
			action="${path}/bim/bankOrgInfo/ExcelImport">

			<table class="search-table">
				<tr>
					<th class="wd-20" style="font-size: 12px"><label>导入文件:</label></th>
					<td> 
					 
						
						<input id="filename" type="file" name="filename"    />
						 
					</td>
				
				</tr>
			</table>
		</form>
	</div>
</div>


