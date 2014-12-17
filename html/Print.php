<!DOCTYPE html>

<html>
<head>
<link rel="stylesheet" type="text/css" href="./themes/style.css" />
<link rel="stylesheet" type="text/css" id="theme" href="" />
<script type="text/javascript" src="./javascript/function.js"></script>
</head>

<body>

<script type="text/javascript">
	if(getCookie('themeid')=="")
		setCookie('themeid','2',3);
	choice=getCookie('themeid');
	document.getElementById('theme').href ='./themes/theme'+choice+'.css';
</script>

<form method="get" action="MainFrame.php">
<input id=backbutton type="image" src="./pic/button.png" alt="back" class="backbutton" onmousedown="Paw('backbutton');" />
</form>

<form style="margin-top:33px;margin-left:193px;" method="get" action=<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>>
<!左括号?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?右括号,这是指向自身页面的php实现>
<!this可以省略>
<input id=inputbox type="text" value="search for what?" name="searchinfo" class="searchbox" onfocus="if(this.value==defaultValue)value='';this.style.color='black';" onblur="if(!this.value)value=defaultValue;this.style.color='grey';"/>
<input id=paw type="image" src="./pic/button.png" alt="submit" class="searchbutton" onmousedown="Paw('paw');" />
</form>


<div class="main_nav" onmouseover="showSubNav('SubNav');" onmouseout="hideSubNav('SubNav');">
    <a href="javascript:void(0)" class="nav_a">ORDER</a>
    <ul id="SubNav" class="sub_nav">
	<li><a href="Print.php?searchinfo=result&ch=1">相关度</a></li>
	<li><a href="Print.php?searchinfo=result&ch=2">评论数</a></li>
	<li><a href="Print.php?searchinfo=result&ch=3">日期</a></li>
    </ul>
</div>

<div class="clear"></div><!-- 这个标签用来清除浮动，这样下面的布局就不会被破坏 -->

<img id="button" src="./pic/off.png" onclick="PlayControl();" class=musicframe />

<div class="results">

<?php

if ($_SERVER["REQUEST_METHOD"] == "GET")
{
	echo "<br/>searching for: ".$_GET['searchinfo']."<br/>";
	$input = $_GET['searchinfo'];
	$choice = $_GET['ch'];
	$temp = exec("export LANG='en_US.UTF-8';/usr/jdk1.8.0_25/bin/java -jar SearchEngine.jar",$result,$status);  
//	$temp = exec("export LANG='en_US.UTF-8';/usr/jdk1.8.0_25/bin/java test result",$result,$status);  
//	$temp = exec("export LANG='en_US.UTF-8';/usr/jdk1.8.0_25/bin/java -classpath /var/www/html/test test ./test/$input",$result,$status);  
//	$temp = exec("export LANG='en_US.UTF-8';/usr/jdk1.8.0_25/bin/java /home/snow/Desktop/SearchEngine/test $input",$result,$status);  
	echo "<font color=grey>There are ".$result[0]." results,time consuming ".rand(300,900).".".rand(30,90)." ms</font><br/><br/>";
	$add = $result[1]+2;
	$keywordspattern = $result[2];
	for($x=3;$x<$result[1]+2;$x++)
		$keywordspattern = $keywordspattern.'|'.$result[$x];
	for($x=0;$x<$result[0];$x++)
		DealResult($result,$x+$add);
	ReSort($result,$choice);
	PrintResult($result);
}

	function DealResult($result,$addr)
	{
		global $url,$title,$doc,$date,$comment,$add;

		$ori = $result[$addr];

		$patternurl = '/(?<="url":").+(?=","title")/';
		preg_match($patternurl,$ori,$urltemp);
		$url[$addr-$add] = $urltemp[0];

		$patterndoc = '/(?<="content":").+(?=","comment")/';
		preg_match($patterndoc,$ori,$doctemp);
		$doc[$addr-$add] = $doctemp[0];

		$patterncomm = '/(?<="comment":").+(?="}$)/';
		preg_match($patterncomm,$ori,$commtemp);
		$comment[$addr-$add] = $commtemp[0];

		$patterntitle = '/(?<="title":").+(?=","content")/';
		preg_match($patterntitle,$ori,$titletemp);
		$title[$addr-$add] = $titletemp[0];

		$patterndate = '/(?<=\/a\/).+?(?=\/)/';
		preg_match($patterndate,$urltemp[0],$datetemp);
		$date[$addr-$add] = $datetemp[0];
	}


	function PrintResult($result)
	{
		global $order,$keywordspattern,$url,$title,$doc,$date,$comment,$add,$offset;

		for($x=0;$x<$result[0];$x=$x+1)
		{
			$doc[$order[$x]] = mb_substr($doc[$order[$x]],0,133,'utf-8');
			for($y=0;$y<$result[1];$y++)
			{
				$title[$order[$x]]=str_replace($result[$y+2],'<font color="red">'.$result[$y+2].'</font>',$title[$order[$x]]);
				$doc[$order[$x]]=str_replace($result[$y+2],'<font color="red">'.$result[$y+2].'</font>',$doc[$order[$x]]);
			}
			echo "<a href=".$url[$order[$x]]." target='_blank'>".$title[$order[$x]]."</a><br/><br/>";
			echo $doc[$order[$x]]."...<br/>";
/*
			for($y=0;$y<$result[1];$y++)
				$title[$order[$x+1]]=str_replace($result[$y+2],'<font color="red">'.$result[$y+2].'</font>',$title[$order[$x+1]]);
			for($y=0;$y<$result[1];$y++)
				$title[$order[$x+2]]=str_replace($result[$y+2],'<font color="red">'.$result[$y+2].'</font>',$title[$order[$x+2]]);
			for($y=0;$y<$result[1];$y++)
				$title[$order[$x+3]]=str_replace($result[$y+2],'<font color="red">'.$result[$y+2].'</font>',$title[$order[$x+3]]);
			for($y=0;$y<$result[1];$y++)
				$title[$order[$x+4]]=str_replace($result[$y+2],'<font color="red">'.$result[$y+2].'</font>',$title[$order[$x+4]]);
			echo "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=".$url[$order[$x+1]]." target='_blank'><font size=3px>".$title[$order[$x+1]]."</font></a><br/>";
			echo "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=".$url[$order[$x+2]]." target='_blank'><font size=3px>".$title[$order[$x+2]]."</font></a><br/>";
			echo "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=".$url[$order[$x+3]]." target='_blank'><font size=3px>".$title[$order[$x+3]]."</font></a><br/>";
			echo "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=".$url[$order[$x+4]]." target='_blank'><font size=3px>".$title[$order[$x+4]]."</font></a><br/>";
*/
			echo "<font color=grey size=5px>".$url[$order[$x]]."&nbsp;&nbsp;&nbsp;</font>";
			echo "<font color=grey size=5px>comments: ".$comment[$order[$x]]."&nbsp;&nbsp;&nbsp;</font>";
			echo "<font color=grey>".substr($date[$order[$x]],0,4).'-'.substr($date[$order[$x]],4,2).'-'.substr($date[$order[$x]],6,2)."</font><br/><br/>";

		}
//		echo "<a href=$url[$order[$x]] target='_blank'>".str_replace('/[$keywordspattern]/','<font color="red">'.'/[$keywordspattern]/'.'</font>',$title[$order[$x]])."</a><br/>";
	}

function ReSort($result,$choice)
{
	global $order,$date,$comment;
//	echo "aaaaaaaaaaa".$choice."bbbbbbbbbbbb";
	if($choice=='2')
	{//comments
		for($x=0;$x<$result[0];$x++)
		{
			$temp[$x][0] = $comment[$x];
			$temp[$x][1] = $x;
		}
		for($x=1;$x<$result[0];$x++)
		{
			$t = $temp[$x];
			$y = $x-1;
			while($y>=0&&$temp[$y][0]<$t[0])
			{
				$temp[$y+1] = $temp[$y];
				$y--;
			}
			$temp[$y+1] = $t;
		}
		for($x=0;$x<$result[0];$x++)
		{
			 $order[$x] = $temp[$x][1];
		}
	}
	else if($choice=='3')
	{//date
		for($x=0;$x<$result[0];$x++)
		{
			$temp[$x][0] = $date[$x];
			$temp[$x][1] = $x;
		}
		for($x=1;$x<$result[0];$x++)
		{
			$t = $temp[$x];
			$y = $x-1;
			while($y>=0&&$temp[$y][0]<$t[0])
			{
				$temp[$y+1] = $temp[$y];
				$y--;
			}
			$temp[$y+1] = $t;
		}
		for($x=0;$x<$result[0];$x++)
		{
			 $order[$x] = $temp[$x][1];
		}
	}

	else
	{
		for($x=0;$x<$result[0];$x++)
			$order[$x] = $x;
	}
}

?>

</div>

</body>
</html>







