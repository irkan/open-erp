<%--
  Created by IntelliJ IDEA.
  User: irkan.ahmadov
  Date: 30.08.2019
  Time: 0:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>404</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="<c:url value="/assets/css/demo4/pages/error/error-6.css" />" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/assets/css/demo4/style.bundle.css" />" rel="stylesheet" type="text/css"/>
    <link rel="shortcut icon" href="<c:url value="/assets/media/logos/logo_sual_32_T28_icon.ico" />"/>
</head>
<body class="kt-page--loading-enabled kt-page--loading kt-quick-panel--right kt-demo-panel--right kt-offcanvas-panel--right kt-header--fixed kt-header--minimize-menu kt-header-mobile--fixed kt-subheader--enabled kt-subheader--transparent kt-page--loading">
<div class="kt-grid kt-grid--ver kt-grid--root kt-page">
    <div class="kt-grid__item kt-grid__item--fluid kt-grid  kt-error-v6"
         style="background-image: url(<c:url value="/assets/media/error/bg6.jpg" />);">
        <div class="kt-error_container">
            <div class="kt-error_subtitle kt-font-light">
                <h1>Səhifə tapılmadı...</h1>
            </div>
            <p class="kt-error_description kt-font-light">
                Sistem inzibatçısı ilə əlaqə saxlayın!
            </p>
        </div>
    </div>
</div>
<script src="<c:url value="/assets/vendors/general/jquery/dist/jquery.js" />" type="text/javascript"></script>
<script src="<c:url value="/assets/vendors/general/bootstrap/dist/js/bootstrap.min.js" />" type="text/javascript"></script>
</body>
</html>