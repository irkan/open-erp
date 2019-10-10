<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="utl" uri="/WEB-INF/tld/Util.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="include/header.jsp" %>
</head>
<body style="font-family: 'Proxima Nova', Verdana; background-image: url(<c:url value="/assets/media/demos/demo4/header.jpg" />); background-position: center top; background-size: 100% 300px;"
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

<form id="delete-form" method="post" action="/delete/<c:out value="${page}"/>">
    <input type="hidden" name="deletedId" id="deletedId">
</form>

<script>
    function submit(form){
        swal.fire({
            text: 'Proses davam edir...',
            onOpen: function() {
                swal.showLoading();
                $(form).submit();
            }
        });
    }
    function create(form, modal, modal_title){
        $('#' + modal).find(".modal-title").html(modal_title);
        $('#' + modal).modal('toggle');
    }

    function edit(form, data, modal, modal_title){
        data = data.replace(/\&#034;/g, '"');
        var obj = jQuery.parseJSON(data);
        console.log(obj);
        $.each( $(form).find("input,select,textarea"), function( key, element ) {
            //console.log($(element).attr("name"));
            var tagName = $(element).prop("tagName");
            var name = $(element).attr("name").split(".");
            var value;
            if(name.length===1){
                value = obj[name[0]];
            } else if(name.length===2){
                console.log(name[0].length);
                value = obj[name[0]][name[1]];
            } else if(name.length===3){
                value = obj[name[0]][name[1]][name[2]];
            } else if(name.length===4){
                value = obj[name[0]][name[1]][name[2]][name[3]];
            }
            //console.log(value);
            if(tagName.toLowerCase()==="input"){
                if($(element).attr("type")==="checkbox"){

                } else if($(element).attr("type")==="radio"){
                    $("input[name='"+$(element).attr("name")+"'][value='"+value.id+"']").prop('checked', true);
                } else if($(element).attr("date")==='date') {
                    $(element).val(getFormattedDate(new Date(value)));
                } else {
                    $(element).val(value);
                }
            } else if(tagName.toLowerCase()==="textarea"){
                $(element).val(value);
            } else if(tagName.toLowerCase()==="select"){
                if(value!=null){
                    $("#"+$(element).attr("id")+" option[value="+value.id+"]").attr("selected", "selected");
                }
            }
        });
        $('#' + modal).find(".modal-title").html(modal_title);
        $('#' + modal).modal('toggle');
    }

    function deleteData(id, info){
        $("#deletedId").val(id);
        swal.fire({
            title: 'Əminsinizmi?',
            html: info + "<br\><br\>Məlumatı geri qaytara bilməyəcəksiniz!",
            type: 'error',
            allowEnterKey: true,
            showCancelButton: true,
            buttonsStyling: false,
            cancelButtonText: 'İmtina et',
            cancelButtonColor: '#d1d5cf',
            cancelButtonClass: 'btn btn-default',
            confirmButtonText: 'Bəli, silinsin!',
            confirmButtonColor: '#c40000',
            confirmButtonClass: 'btn btn-danger',
            footer: '<a href>Məlumatlar yenilənsinmi?</a>'
        }).then(function(result) {
            if (result.value) {
                swal.fire({
                    text: 'Proses davam edir...',
                    onOpen: function() {
                        swal.showLoading();
                        $("#delete-form").submit();
                    }
                })
            }
        })
    }

    function getFormattedDate(date) {
        var year = date.getFullYear();

        var month = (1 + date.getMonth()).toString();
        month = month.length > 1 ? month : '0' + month;

        var day = date.getDate().toString();
        day = day.length > 1 ? day : '0' + day;

        return day + '.' + month + '.' + year;
    }
</script>


</body>
</html>
