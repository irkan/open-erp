<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="uj" uri="/WEB-INF/tld/UtilJson.tld"%>
<%@ taglib prefix="ua" uri="/WEB-INF/tld/UserAccess.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="include/header.jsp" %>
</head>
<body style="background-image: url(<c:url value="/assets/media/demos/demo4/header.jpg" />); background-position: center top; background-size: 100% 300px;"
      class="kt-page--loading-enabled kt-page--loading kt-quick-panel--right kt-demo-panel--right kt-offcanvas-panel--right kt-header--fixed kt-header--minimize-menu kt-header-mobile--fixed kt-subheader--enabled kt-subheader--transparent kt-page--loading">
<div id="kt_header_mobile" class="kt-header-mobile  kt-header-mobile--fixed ">
    <div class="kt-header-mobile__logo">
        <a href="№">
            <img alt="Logo" src="<c:url value="/assets/media/logos/logo-sual-32.png" />" />
        </a>
    </div>
    <div class="kt-header-mobile__toolbar">

        <button class="kt-header-mobile__toolbar-toggler" id="kt_header_mobile_toggler"><span></span></button>
        <button class="kt-header-mobile__toolbar-topbar-toggler" id="kt_header_mobile_topbar_toggler"><i
                class="flaticon-more-1"></i></button>
    </div>
</div>
<div class="kt-grid kt-grid--hor kt-grid--root">
    <div class="kt-grid__item kt-grid__item--fluid kt-grid kt-grid--ver kt-page">
        <div class="kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor kt-wrapper" id="kt_wrapper">

            <%@include file="include/menu.jsp" %>

            <%@include file="include/js.jsp" %>

            <%@include file="include/body.jsp" %>

            <%@include file="include/footer.jsp" %>
        </div>
    </div>
</div>

<div id="kt_scrolltop" class="kt-scrolltop">
    <i class="fa fa-arrow-up"></i>
</div>

<script>
    function edit(form, data, modal, modal_title){
        var obj = jQuery.parseJSON(data);
        $.each( $(form).find("input,select,textarea"), function( key, element ) {
            let tagName = $(element).prop("tagName");
            let name = $(element).attr("name").split(".");
            let value;
            if(name.length===1){
                value = obj[$(element).attr("name")];
            } else if(name.length===2){
                value = obj[name[0]][name[1]];
            } else if(name.length===3){
                value = obj[name[0]][name[1]][name[2]];
            } else if(name.length===4){
                value = obj[name[0]][name[1]][name[2]][name[3]];
            }
            if(tagName.toLowerCase()==="input"){
                if($(element).attr("type")==="checkbox"){

                } else if($(element).attr("type")==="radio"){
                    $("input[name='"+$(element).attr("name")+"'][value='"+value.id+"']").prop('checked', true);
                } else {
                    $(element).val(value);
                }
            } else if(tagName.toLowerCase()==="textarea"){
                $(element).val(value);
            } else if(tagName.toLowerCase()==="select"){
                $("#"+$(element).attr("id")+" option[value="+value.id+"]").attr("selected", "selected");
            }
        });
        $('#' + modal).find(".modal-title").html(modal_title);
        $('#' + modal).modal('toggle');
    }
</script>


</body>
</html>
