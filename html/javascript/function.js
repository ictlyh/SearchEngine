
	function setCookie(c_name, value, expiredays)
	{
		var exdate=new Date();
		exdate.setDate(exdate.getDate() + expiredays);
		document.cookie=c_name+ "=" + escape(value) + ((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
	}
	function getCookie(c_name)
	{
		if (document.cookie.length>0)					//先查询cookie是否为空，为空就return ""
		{
			c_start=document.cookie.indexOf(c_name + "=")	//通过String对象的indexOf()来检查这个cookie是否存在，不存在就为 -1　　
			if (c_start!=-1)
			{ 
				c_start=c_start + c_name.length+1;		//最后这个+1其实就是表示"="号啦，这样就获取到了cookie值的开始位置
				c_end=document.cookie.indexOf(";",c_start);	//指定的开始索引的位置 通过";"号是否存在来判断
				if (c_end==-1) c_end=document.cookie.length　　
					return unescape(document.cookie.substring(c_start,c_end));　　
			}
		}
		return "";
	}
	function SwitchTheme(id)
	{
		setCookie('themeid',id,3);
		document.getElementById('theme').href ='./themes/theme'+id+'.css';
		self.parent.frames['MusicFrame'].document.getElementById('music').src='./misc/'+id+'.mp3';
		document.getElementById('button').src='./pic/off.png';;
	}
	function showSubNav(id)
	{
		document.getElementById(id).style.display='block';
	}
	function hideSubNav(id)
	{
		document.getElementById(id).style.display='none';
	}
	function Paw(id)
	{
		document.getElementById(id).src="./pic/button_down.png";
	}
	function PlayControl()
	{
		pic=document.getElementById('button');
		msc=self.parent.frames['MusicFrame'].document.getElementById('music');
		if(pic.src.match("off"))
		{
			msc.pause();
			pic.src='./pic/on.png';
		}
		else
		{
			msc.play();	
			pic.src='./pic/off.png';
		}
	}

function Sort(choice)
{
	var xmlhttp;
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	}
	else
	{// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			document.getElementById("myDiv").innerHTML=xmlhttp.responseText;
		}
	}
	xmlhttp.open("GET","/ajax/demo_get.asp",true);
	xmlhttp.send();
}

















