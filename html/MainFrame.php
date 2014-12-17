<!DOCTYPE html>

<html>
<head>
<link rel="stylesheet" type="text/css" href="./themes/style.css" />
<link rel="stylesheet" type="text/css" id="theme" href="" />
<script type="text/javascript" src="./javascript/function.js"></script>
</head>

<body>

<?php
/*$a = exec("pwd",$out,$status); 
print_r($a);
echo "<br/>";  
print_r($out);
echo "<br/>";  
print_r($status);
echo "<br/>";  

//phpinfo();
//$a = exec("./a.out",$out,$status);  
//$a = exec("/usr/jdk1.8.0_25/bin/java MyFirstJavaProgram -Dfile.encoding=UTF-8",$out,$status);  
//$a = exec("ls",$out,$status);  
//print_r($a);
//echo "<br/>";  
$a = exec("export LANG='en_US.UTF-8';/usr/jdk1.8.0_25/bin/java test sample",$out,$status);  
print_r($out);
echo "<br/>";  
print_r($status);
echo "<br/>";  
*/
/*
	$temp = exec("export LANG='en_US.UTF-8';/usr/jdk1.8.0_25/bin/java test sample",$result,$status);  
	echo "There is ".$result[0]." results for ya:<br/><br/>";	
	$add = $result[1]+2;
	$keywordspattern = $result[2];
	for($x=3;$x<$result[1]+2;$x++)
		$keywordspattern = $keywordspattern.'|'.$result[$x];
	for($x=0;$x<$result[0];$x++)
		$order[$x] = $x;
///*
	echo $keywordspattern."<br/><br/>";	
	for($x=0;$x<$result[0];$x++)
	{
		DealResult($result,$x+$add);
//		echo $url[$x]."<br/><br/>";		
//		echo $doc[$x]."<br/><br/>";		
//		echo $comment[$x]."<br/><br/>";		
//		echo $title[$x]."<br/><br/>";		
//		echo $date[$x]."<br/><br/>";		
	}

	PrintResult($result);

	function DealResult($result,$addr)
	{
		global $url,$title,$doc,$date,$comment,$add,$offset;

		$ori = $result[$addr];

		$patternurl = '/(?<="url": ").+(?=", "content")/';
		preg_match($patternurl,$ori,$urltemp);
		$url[$addr-$add] = $urltemp[0];

		$patterndoc = '/(?<="content": ").+(?=", "comment")/';
		preg_match($patterndoc,$ori,$doctemp);
		$doc[$addr-$add] = $doctemp[0];

		$patterncomm = '/(?<="comment": ").+(?=", "title")/';
		preg_match($patterncomm,$ori,$commtemp);
		$comment[$addr-$add] = $commtemp[0];

		$patterntitle = '/(?<="title": ").+(?="}$)/';
		preg_match($patterntitle,$ori,$titletemp);
		$title[$addr-$add] = $titletemp[0];

		$patterndate = '/(?<=\/a\/).+?(?=\/)/';
		preg_match($patterndate,$urltemp[0],$datetemp);
		$date[$addr-$add] = $datetemp[0];
			//echo $result[$addr]."<br/>";
			//echo "<a href=$url target='_blank'>".str_replace($keyword,'<font color="red">'.$keyword.'</font>',$title)."</a><br/>";
			//$abstract = fgets($fp);
			//echo str_replace($keyword,'<font color="red">'.$keyword.'</font>',$abstract)."<br/><br/><br/>";
	}

	function PrintResult($result)
	{
		global $order,$keywordspattern,$url,$title,$doc,$date,$comment,$add,$offset;

		for($x=0;$x<$result[0];$x++)
		{
			$doc[$order[$x]] = mb_substr($doc[$order[$x]],0,133,'utf-8');
			for($y=0;$y<$result[1];$y++)
			{
				$title[$order[$x]]=str_replace($result[$y+2],'<font color="red">'.$result[$y+2].'</font>',$title[$order[$x]]);
				$doc[$order[$x]]=str_replace($result[$y+2],'<font color="red">'.$result[$y+2].'</font>',$doc[$order[$x]]);
			}
			echo $title[$order[$x]]."<br/><br/>";
			echo $doc[$order[$x]]."...<br/><br/>";
		}
//		echo "<a href=$url[$order[$x]] target='_blank'>".str_replace('/[$keywordspattern]/','<font color="red">'.'/[$keywordspattern]/'.'</font>',$title[$order[$x]])."</a><br/>";
	}
//*/
?> 

<script type="text/javascript">
	if(getCookie('themeid')=="")
		setCookie('themeid','4',3);
	choice=getCookie('themeid');
	document.getElementById('theme').href ='./themes/theme'+choice+'.css';
</script>

<div class="main_nav" onmouseover="showSubNav('SubNav');" onmouseout="hideSubNav('SubNav');">
    <a href="javascript:void(0)" class="nav_a">STYLE</a>
    <ul id="SubNav" class="sub_nav">
	<li><a href="javascript:void(0)" onclick="SwitchTheme(1)">后核战时代</a></li>
	<li><a href="javascript:void(0)" onclick="SwitchTheme(2)">后现代主义</a></li>
	<li><a href="javascript:void(0)" onclick="SwitchTheme(3)">s牌自燃机</a></li>
	<li><a href="javascript:void(0)" onclick="SwitchTheme(4)">文艺？？1</a></li>
	<li><a href="javascript:void(0)" onclick="SwitchTheme(5)">文艺？？2</a></li>
    </ul>
</div>

<div class="clear"></div><!-- 这个标签用来清除浮动，这样下面的布局就不会被破坏 -->

<img id="button" src="./pic/off.png" onclick="PlayControl();" class=musicframe />


<!************************************正文内容*****************************************>
<form class="searcharea" method="get" action="Print.php">
<!左括号?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?右括号,这是指向自身页面的php实现>
<!this可以省略>
<input id=inputbox type="text" value="search for what?" name="searchinfo" class="searchbox" onfocus="if(this.value==defaultValue)value='';this.style.color='black';" onblur="if(!this.value)value=defaultValue;this.style.color='grey';"/>
<input id=paw type="image" src="./pic/button.png" alt="submit" class="searchbutton" onmousedown="Paw('paw');" />
</form>
<!**************************************************************************************>


</body>
</html>

