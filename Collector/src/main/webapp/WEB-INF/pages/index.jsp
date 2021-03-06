<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Kmunda Workflow Engine</title>

<!-- Bootstrap Core CSS -->
<link href="<c:url value="/resources/css/bootstrap.min.css" />"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="<c:url value="/resources/css/openhab.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">

</head>

<body>
	<div id="wrapper">
		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0" id="navibar">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#collapsed-navigation">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
			</div>
			<div align="center">
				<h2>Ksilence Collector</h2>
			</div>

			<div class="sidebar" role="navigation" id="testb">
				<div class="collapse navbar-collapse" id="collapsed-navigation" style="margin-top: 10px; margin-left: 10px; align: top">
					<h4>Collector Service</h4>
					<ul class="nav">
						<li><div>
								<a href="start.htm">Collection starten</a>
							</div></li>
						<li><div>
								<a href="stop.htm">Collection stoppen</a>
							</div></li>
						<li><div>
								<a href="switch.htm">MQTT Gateway umschalten</a>
							</div></li>
						<li><div>
								<a href="publish.htm">Sampledaten publishen</a>
							</div></li>
					</ul>
					<h4><br></h4>
					<h4>Hauptseite</h4>
					<ul class="nav">
						<li><div>
								<a href="http://dgf-vsw.azurewebsites.net/">Zur&uuml;ck</a>
							</div></li>
					</ul>
				</div>
				<!-- /.sidebar-collapse -->
			</div>
			<!-- /.navbar-static-side -->
		</nav>
	</div>

	<div id="page-wrapper">
		<div class="row">
			<div class="col-lg-12" id="anzfeld">
				<h3>${message}</h3>
				MQTT Gateway: ${gateway}
			</div>
		</div>
		<!-- /.row -->
	</div>
	<!-- /#page-wrapper -->
</body>
</html>