/**
 * Created by Administrator on 2017/10/5.
 */
var myUtil = new function() {
    this.defautLimit = 10;//默认的分页条数
    this.getLaypageConfig = function (config) {
        /**
         * 分页参数文件的默认参数定义
         */
        var laypageConfig = {
            cont: $('#pagination-box'),//容器。值支持id名、原生dom对象，jquery对象,
            pages: 0,//需要修改
            skip: false,
            skin: '#0474cf',
            groups: 5,
            curr:1,
            first: 1,//如需显示首页数字，需要修改为对应的数字，否则设置为false
            last: false,//如需显示尾页数字，需要修改为对应的数字，否则设置为false
            prev: '<<上一页',
            next: '下一页>>',
            jump: function (obj, first) {
                console.log(obj.curr);//当前页
                console.log(first);
                console.error("跳转函数没有定义！");
                ////判断first，可防止初始时会无限刷新
            }
        };
        $.extend(laypageConfig, config);
        return laypageConfig;
    };




    //动态设置窗口高度，这个后来不使用了。
    this.setIframeHeight=function (frameId) {
        var iframe = document.getElementById(frameId);
        if (iframe) {
            var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
            if (iframeWin.document.body) {
                iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
            }
            //console.log("设置高度：",iframe.height);
        }
    };
    
    
    //将有参数的函数封装为无参数的函数，用于为事件传递参数
    this.createFunction = function (obj,strFunc){  //其中obj接收函数归属的对象，strFunc接收要处理事件的函数  
	    var args=[]; //定义args用于存储传递给事件处理程序的参数  
	    if(!obj)obj=window; //如果是全局函数则obj=window;  
	    //得到传递给事件处理程序的参数  
	    for(var i=2;i<arguments.length;i++)args.push(arguments[i]);  
	    //用无参数函数封装事件处理程序的调用  
	    return function(){  
	    	obj[strFunc].apply(obj,args); //这句代码是关键。将参数传递给指定的事件处理程序  
	    }  
    };
    
    /**
     * 根据键的名称、键值查找，并删除指定的数组元素。只删一个
     * 如果删除成功，返回删除元素的下标。否则，返回-1
     */
	this.arraySplice = function(array,name,value){
		for(var i=0;i<array.length;i++){
			if(array[i][name]==value){
				array.splice(i,1);
				return i;
			}
		}
		return -1;
	};

};

function parseJSON(str){
	return eval("("+str+")");
};

function getCurrUser(){
	var userObject = {};
	userObject = $.extend(true,{},window.parent.user);
	return userObject;
};

function getConst(){
	var hashConst = {};
	hashConst = $.extend(true,{},window.parent.hashConst);
	return hashConst;
};

Date.prototype.format = function(fmt) { 
    var o = { 
       "M+" : this.getMonth()+1,                 //月份 
       "d+" : this.getDate(),                    //日 
       "h+" : this.getHours(),                   //小时 
       "m+" : this.getMinutes(),                 //分 
       "s+" : this.getSeconds(),                 //秒 
       "q+" : Math.floor((this.getMonth()+3)/3), //季度 
       "S"  : this.getMilliseconds()             //毫秒 
   }; 
   if(/(y+)/.test(fmt)) {
           fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
   }
    for(var k in o) {
       if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
   return fmt; 
}

/**
 * javascriptPOST方式提交数据
 * 
 * @param url
 * @param args
 * @returns
 */
function jsPost (url,args)  
{  
    var myForm = document.createElement("form");  
    myForm.method = "post";  
    myForm.action = url;  
    for ( var k in args) {  
        var myInput = document.createElement("input");  
        myInput.setAttribute("name", k);  
        myInput.setAttribute("value", args[k]);  
        myForm.appendChild(myInput);  
    }  
    document.body.appendChild(myForm);  
    myForm.submit();  
    document.body.removeChild(myForm);  
} 

function getRequestParams() {  
	   var url = location.search; //获取url中"?"符后的字串  
	   var theRequest = new Object();  
	   if (url.indexOf("?") != -1) {  
	      var str = url.substr(1);  
	      strs = str.split("&");  
	      for(var i = 0; i < strs.length; i ++) {  
	         theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);  
	      }  
	   }  
	   return theRequest;  
	}
function getRequestParamByName(name) { 
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
    var r = window.location.search.substr(1).match(reg); 
    if (r != null) return unescape(r[2]); 
    return null; 
}
/**
 * pdf查看方法
 * 
 * @param archiveType
 * @param fileId
 * @param applyId
 * @param showFileSecretPages
 */
function pdfSeen(archiveType,fileId,applyId,showFileSecretPages){
	var requestId = (new Date()).getTime()+"";
	var checkUrl = '/pdf/pdfshow.html?requestId='+requestId;
	if(!window.requestParams){
		window.requestParams={};
	}
	console.log(window.requestParams);
	window.requestParams[requestId]={}
	window.requestParams[requestId].fileId = fileId;
	window.requestParams[requestId].archiveType = archiveType;
	window.requestParams[requestId].applyId = applyId?applyId:"";
	window.requestParams[requestId].showFileSecretPages = showFileSecretPages?showFileSecretPages:false;
	//window.fileId = fileId;
	//window.archiveType = archiveType;f
	//window.applyId = applyId?applyId:"";
	window.open(checkUrl);
}


function setCookie(name,value,days)
{
	days = days?days:30;
	var exp = new Date();
	exp.setTime(exp.getTime() + days*24*60*60*1000);
	document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}

function getCookie(name)
{
	var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	if(arr=document.cookie.match(reg))
		return unescape(arr[2]);
	else
		return null;
}

function delCookie(name)
{
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval=getCookie(name);
	if(cval!=null)
	document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}

