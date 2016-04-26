<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="ch">
<%@ include file="../common/meta.jsp"%>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/admin/js/ace/admin.guest.js"></script>

 <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/ueditor/lang/zh-cn/zh-cn.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
		 var ue = UE.getEditor('context');
		if("${tip}" != null && "${tip}" != ""){
			noty({"text":"${tip}","layout":"top","type":"success","timeout":"2000"});
		}
		$(".date").datetimepicker({
			language:  'zh-CN',
	        weekStart: 1,
	        todayBtn:  1,
	        format:'yyyy-mm-dd',
			autoclose: 1,
			todayHighlight: 1,
			startView: 2,
			minView: 2,
			forceParse: 0
	    });
	});
</script>
</head>
<body>
	<div class="layout">
		<!-- top -->
		<%@ include file="../top.jsp"%>
		<!-- 导航 -->
		<%@ include file="../menu.jsp"%>
		
		<input type="hidden" id="hf_id" />

		<div class="main-wrapper">
			<div class="container-fluid">
				<div class="row-fluid ">
					<div class="span12">
						<div class="content-widgets light-gray">
							<div class="widget-head  bondi-blue" >
								<h3>教师登记</h3>
							</div>
							
							<div class="widget-container">
								
							<form     enctype="multipart/form-data"  class='form-horizontal' action="${pageContext.request.contextPath}/admin/teacher/create" method="post" id=''>
							<input type="hidden"  name='id' id="id" value="">
							
							
							<div class="control-group">
								<label for="title" class="control-label">教师名称：</label>
								<div class="controls">
									 <input type="file" id="firstDemoImgFile" name="imgfile">
								</div>
							</div>
							<div class="control-group">
								<label for="title" class="control-label">教师名称：</label>
								<div class="controls">
									<input type="text" name='name' id="name" placeholder="">
								</div>
							</div>
							
							<div class="control-group">
								<label for="title" class="control-label">email：</label>
								<div class="controls">
									<input type="text" name='email' id="email" placeholder="">
								</div>
							</div>
							
							<div class="control-group">
								<label for="title" class="control-label">电话：</label>
								<div class="controls">
									<input type="text" name='tel' id="tel" placeholder="">
								</div>
							</div>
							<div class="control-group">
								<label for="title" class="control-label">职称：</label>
								<div class="controls">
									<select name="level" id='level'>
										<option value="" ></option>
										<option value="讲师" >讲师</option>
										<option value="中级讲师" >中级讲师</option>
										<option value="高级讲师" >高级讲师</option>
										<option value="特级讲师" >特级讲师</option>
									</select>
								</div>
							</div>
							
							<div class="control-group">
								<label for="title" class="control-label">工作年限：</label>
								<div class="controls">
									<select name="year" id='year' >
										<option value="" ></option>
										<option value="1到5年" >1到5年</option>
										<option value="5年以上" >5年以上</option>
										<option value="10年以上" >10年以上</option>
									</select>
								</div>
							</div>
							<div class="control-group">
								<label for="title" class="control-label">工作经历：</label>
								<div class="controls">
								<textarea id='work' rows="3" cols="" name='work'></textarea>
								</div>
							</div>
							
							<div class="control-group">
								<label for="title" class="control-label">项目经历：</label>
								<div class="controls">
								<textarea id='project' rows="3" cols="" name='project'></textarea>
								</div>
							</div>
							
							<div class="control-group">
								<label for="title" class="control-label">个人简介：</label>
								<div class="controls">
								<textarea  id='remark' rows="3" cols="" name='remark'></textarea>
								</div>
							</div>
								<div class="control-group">
								<label for="title" class="control-label">承担课程：</label>
								<div class="controls">
								<textarea  id='remark' rows="3" cols="" name='remark1'></textarea>
								</div>
							</div>
							
							<div class="control-group">
								<div class="controls">
								<input class='btn ' type="submit" value="提交">
								</div>
							</div>
							
						</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="../foot.jsp"%>
	</div>
</body>
</html>