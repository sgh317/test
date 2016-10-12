<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ include file="/WEB-INF/views/comm/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/comm/meta.jsp"%>
<html>
<head>
	<title>菜单部署</title>
	<link rel="stylesheet" href="${_ContextPath}/CacheableResource/simplePagination/simplePagination.css"/> 
	<link rel="stylesheet" href="${_ContextPath}/CacheableResource/util/jquery.tree.css"/> 
	<script src="${_ContextPath}/CacheableResource/jquery200/jquery-1.9.1.js"></script>
	<script src="${_ContextPath}/CacheableResource/util/jquery.tree.js"></script>
	<script src="${_ContextPath}/CacheableResource/rpc/jquery-sso-patch.js"></script>
	<script src="${_ContextPath}/CacheableResource/rpc/JsonStub.js"></script>
	<script src="${_ContextPath}/PUBLIC/JsonBeanStub/MenuWXJsBean.js"></script>
	<style type="text/css">
		table tr td {
			white-space: nowrap;
			padding: 2px 5px;
		}
	</style>

<script type="text/javascript">
var menuCount = 0;
var menuMap = {id:'Root', name:'ROOT', pId: '', pName: '', index: '1', key: 'root', type: 'click', subMenus: []};

$(document).ready(function(){
	// testData();	
	setTimeout(function(){
		RpcContext.getBean('MenuWXJsBean').queryWXMenu({
	    	onSucess:function(rt)
	    	{
	    		var res = eval('('+rt+')'); 
	    		
	    		var userList = res.state;
				if(res.state)
				{
					menuMap = res.value.menuMap;

					initMenus(menuMap);
					$(function(){
						$('#'+menuMap.id).tree({expanded: 'li'});
					});
				}
				else
				{
	    			alert(res.errorMessage);
				}
			},onError:function(err)
			{
				alert(err);
			}
		});
	}, 500);
});

function deployWXMenu()
{
	RpcContext.getBean('MenuWXJsBean').deployWXMenu({
    	onSucess:function(rt)
    	{
    		alert('ok');
		},onError:function(err)
		{
			alert(err);
		}
	});
}

function initMenus(mm)
{
	var h = "";
	if(mm != null && mm.subMenus != null)
	{
		$.each(mm.subMenus,function(index,item){
			item.pId = mm.id;
			h += getHtmlByObj(item);
		});
	}
	$("#"+menuMap.id).html(h);
}


/*** edit HTML ***/
function getHtmlByObj(mm)
{
	var h = "";
	if(mm != null)
	{
		var id = "m_"+(menuCount++);
		mm.id = id.replace('.','_');
		h += "<li id=\""+mm.id+"\">";
		h += "<a href=\"javascript:void(0);\" m-pid=\""+mm.pId+"\" m-parent=\""+mm.pName+"\" m-index=\""+mm.index+"\" m-name=\""+mm.name+"\" m-key=\""+mm.key+"\" m-type=\""+mm.type+"\" title=\""+mm.key+"\">";
		h += mm.name;
		h += "</a>";
		// subMenus
		if(mm.subMenus != null && mm.subMenus.length > 0)
		{
			h += "<ul>";
			$.each(mm.subMenus,function(index,item){
				item.pId = mm.id;
				h += getHtmlByObj(item);
			});
			h += "</ul>";
		}
		
		h += "</li>";
	}
	return h;
}


</script>
</head>
<body>
<div class="area">&nbsp;&nbsp;当前位置：微信平台 / 微信管理 / <span class="text-red">菜单发布</span></div>
    <div>
        <!--左边 开始--->
        <div class="l-content">
            <table cellpadding="0" cellspacing="15" class="table-form">
                <tbody>
					<tr>
	                    <td><input type="button" value="部署到微信" class="subbut" onclick="deployWXMenu()"></td>
	                </tr>
	                <tr>
	                    <td>
							<ul id="Root">
							</ul>
	                    </td>
	                </tr>
	            </tbody>
	        </table>
        </div>
      </div>
</body>
</html>