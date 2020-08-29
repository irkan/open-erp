<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
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
            <img alt="${logo}" src="<c:url value="/assets/media/logos/${logo}-32.png" />" />
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
            allowOutsideClick: false,
            onOpen: function() {
                swal.showLoading();
                $(form).submit();
            }
        });
    }
    function submitTimeoutModal(form, modal, timeout){
        swal.fire({
            text: 'Proses davam edir...',
            allowOutsideClick: false,
            onOpen: function() {
                swal.showLoading();
                $(form).submit();
            }
        });

        setTimeout(function(){
            swal.close();
            $(modal).modal('hide');
        }, timeout);
    }
    /*function clearFilter(form){

        /!*$.each( $(form).find("input,select,textarea"), function( key, element ) {
            var tagName = $(element).prop("tagName");
            if(tagName.toLowerCase()==="input"){
                if($(element).attr("type")==="checkbox"){
                    $("input[name='"+$(element).attr("name")+"']").prop('checked', false);
                } else if($(element).attr("type")==="radio"){
                    $("input[name='"+$(element).attr("name")+"']").prop('checked', false);
                } else if($(element).attr("date")==='date') {
                    $(element).val('');
                } else {
                    $(element).val('');
                }
            } else if(tagName.toLowerCase()==="textarea"){
                $(element).val('');
            } else if(tagName.toLowerCase()==="select"){
                $("#"+$(element).attr("id")+" option:selected").removeAttr("selected");
            }
        })
        submit($(form));*!/
    }*/

    function create(form, data, modal, modal_title){
        $('#' + modal).find(".modal-title").html(modal_title);
        $('#' + modal).modal('toggle');
        fillData(form, data, modal, modal_title);
    }
    function edit(form, data, modal, modal_title){
        $('#' + modal).find(".modal-footer").removeClass("kt-hide");
        fillData(form, data, modal, modal_title);
    }

    function view(form, data, modal, modal_title){
        $('#' + modal).find(".modal-footer").addClass("kt-hide");
        fillData(form, data, modal, modal_title);
    }

    function fillData(form, data, modal, modal_title){
        data = data.replace(/\&#034;/g, '"');
        var obj = jQuery.parseJSON(data);
        console.log(obj);
        $.each( $(form).find("input,select,textarea"), function( key, element ) {
            try{
                var tagName = $(element).prop("tagName");
                var value = findValue(obj, element);
                if(tagName.toLowerCase()==="input"){
                    if($(element).attr("type")==="checkbox"){
                        $("input[name='"+$(element).attr("name")+"']").prop('checked', false);
                        if(value){
                            $("input[name='"+$(element).attr("name")+"']").prop('checked', true);
                        }
                    } else if($(element).attr("type")==="radio"){
                        $("input[name='"+$(element).attr("name")+"'][value='"+value.id+"']").prop('checked', true);
                    } else if($(element).attr("date_")==='date_') {
                        $("input[name='"+$(element).attr("name")+"']").val('');
                        if(value!==null && value.length>0){
                            $(element).val(getFormattedDate(new Date(value)));
                        }
                    } else if($(element).attr("date_")==='datetime_') {
                        $("input[name='"+$(element).attr("name")+"']").val('');
                        if(value!==null && value.length>0){
                            $(element).val(getFormattedDateTime(new Date(value)));
                        }
                    }  else {
                        $("input[name='"+$(element).attr("name")+"']").val('');
                        if($.type(value) !== "undefined" && $.type(value.id) !== "undefined"){
                            value = value.id;
                        }
                        if(value!=null && value.length>0){
                            $(element).val(value);
                        }
                    }
                } else if(tagName.toLowerCase()==="textarea"){
                    $(element).val(value);
                } else if(tagName.toLowerCase()==="select"){
                    $("select[name='"+$(element).attr("name")+"'] option:selected").removeAttr("selected");
                    if($(element).attr("multiple")){
                        $(value).each(function(key, item){
                            $("select[name='"+$(element).attr("name")+"'] option[value="+item.type.id+"]").attr("selected", "selected");
                        });
                        $("select[name='"+$(element).attr("name") +"']").select2();
                    } else {
                        if(value!=null){
                            $("select[name='"+$(element).attr("name")+"'] option[value="+value.id+"]").attr("selected", "selected");
                        }
                    }
                }
            } catch (e) {
                console.error(e);
            }
        });
        $('#' + modal).find(".modal-title").html(modal_title);
        $('#' + modal).modal('toggle');
    }

    function findValue(obj, element){
        var originalName = $(element).attr("name");
        var name = originalName.split(".");
        var value;
        if(name.length===1){
            if(getIndex(name[0])!=null){
                value = obj[name[0]][getIndex(name[0])];
            } else {
                value = obj[name[0]]
            }
        } else if(name.length===2){
            if(getIndex(name[0])!=null){
                value = obj[getName(name[0])][getIndex(name[0])][name[1]];
            } else if(getIndex(name[1])!=null){
                value = obj[name[0]][getName(name[1])][getIndex(name[1])];
            } else {
                value = obj[name[0]][name[1]];
            }
        } else if(name.length===3){
            if(getIndex(name[0])!=null){
                value = obj[getName(name[0])][getIndex(name[0])][name[1]][name[2]];
            } else if(getIndex(name[1])!=null){
                value = obj[name[0]][getName(name[1])][getIndex(name[1])][name[2]];
            } else if(getIndex(name[2])!=null){
                value = obj[name[0]][name[1]][getName(name[2])][getIndex(name[2])];
            } else {
                value = obj[name[0]][name[1]][name[2]];
            }
        } else if(name.length===4){
            if(getIndex(name[0])!=null){
                value = obj[getName(name[0])][getIndex(name[0])][name[1]][name[2]][name[3]];
            } else if(getIndex(name[1])!=null){
                value = obj[name[0]][getName(name[1])][getIndex(name[1])][name[2]][name[3]];
            } else if(getIndex(name[2])!=null){
                value = obj[name[0]][name[1]][getName(name[2])][getIndex(name[2])][name[3]];
            } else if(getIndex(name[3])!=null){
                value = obj[name[0]][name[1]][name[2]][getName(name[3])][getIndex(name[3])];
            } else {
                value = obj[name[0]][name[1]][name[2]][name[3]];
            }
        }
        return value;
    }

    function getIndex(name) {
        var data = name.split("[");
        if (data.length > 1) {
            return parseInt(data[1].split("]")[0]);
        }
        return null;
    }

    function getName(name){
        var data = name.split("[");
        if(data.length>1){
            return data[0];
        }
        return name;
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
            cancelButtonText: 'İmtina',
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
                    allowOutsideClick: false,
                    onOpen: function() {
                        swal.showLoading();
                        $("#delete-form").submit();
                    }
                })
            }
        })
    }

    function getFormattedDate(date) {
        console.log(date);
        var year = date.getFullYear();

        var month = (1 + date.getMonth()).toString();
        month = month.length > 1 ? month : '0' + month;

        var day = date.getDate().toString();
        day = day.length > 1 ? day : '0' + day;

        return day + '.' + month + '.' + year;
    }

    function getFormattedDateTime(date) {
        var year = date.getFullYear();

        var month = (1 + date.getMonth()).toString();
        month = month.length > 1 ? month : '0' + month;

        var day = date.getDate().toString();
        day = day.length > 1 ? day : '0' + day;

        var hours = date.getHours().toString();
        hours = hours.length > 1 ? hours : '0' + hours;

        var minutes = date.getMinutes().toString();
        minutes = minutes.length > 1 ? minutes : '0' + minutes;

        var seconds = date.getSeconds().toString();
        seconds = seconds.length > 1 ? seconds : '0' + seconds;

        return day + '.' + month + '.' + year + ' ' + hours + ':' + minutes + ':' + seconds;
    }


    jQuery(document).ready(function() {
        toastr.options = {
            "closeButton": true,
            "debug": true,
            "newestOnTop": false,
            "progressBar": true,
            "positionClass": "toast-bottom-center",
            "preventDuplicates": true,
            "showDuration": "300",
            "hideDuration": "1000",
            "timeOut": "5000",
            "extendedTimeOut": "1000",
            "showEasing": "swing",
            "hideEasing": "linear",
            "showMethod": "fadeIn",
            "hideMethod": "fadeOut"
        };
        <c:if test="${not empty response}">
            var data = "<c:out value="${utl:toJson(response)}"/>".replace(/\&#034;/g, '"');
            var obj = jQuery.parseJSON(data);
            $.each( obj['messages'], function( key, value ) {
                if(obj['type']==='error'){
                    toastr.error(value);
                } else if(obj['type']==='info'){
                    toastr.info(value);
                } else if(obj['type']==='warning'){
                    toastr.warning(value);
                } else if(obj['type']==='success'){
                    toastr.success(value);
                }
            });
        </c:if>
    });

    var KTAppOptions = {
        "colors": {
            "state": {
                "brand": "#366cf3",
                "light": "#ffffff",
                "dark": "#282a3c",
                "primary": "#5867dd",
                "success": "#34bfa3",
                "info": "#36a3f7",
                "warning": "#ffb822",
                "danger": "#fd3995"
            },
            "base": {
                "label": ["#c5cbe3", "#a1a8c3", "#3d4465", "#3e4466"],
                "shape": ["#f0f3ff", "#d9dffa", "#afb4d4", "#646c9a"]
            }
        }
    };

    var KTSessionTimeoutDemo = function () {
        <fmt:parseNumber var = "sessionIddle" integerOnly = "true" type = "number" value = "${pageContext.session.maxInactiveInterval*1000}" />
        var initDemo = function () {
            $.sessionTimeout({
                title: 'Sessiya Zamanı Xəbərdarlığı',
                message: 'Sessiyanız başa çatmaq üzrədir.',
                logoutButton: 'Çıxış',
                keepAliveButton: 'Davam et',
                keepAliveUrl: 'https://keenthemes.com/metronic/tools/preview/api/session-timeout/keepalive.php',
                logoutUrl: '/logout',
                redirUrl: '/logout',
                warnAfter: ${sessionIddle}, //warn after 5 seconds
                redirAfter: ${sessionIddle+30000}, //redirect after 10 secons,
                ignoreUserActivity: true,
                countdownMessage: 'Bitmə vaxtına {timer} saniyə qalıb.',
                countdownBar: true
            });
        }

        return {
            //main function to initiate the module
            init: function () {
                initDemo();
            }
        };

    }();

    jQuery(document).ready(function() {
        KTSessionTimeoutDemo.init();
    });
    function copyToClipboard(data) {
        var textArea = document.createElement("textarea");
        textArea.value = data;
        document.body.appendChild(textArea);
        textArea.select();
        document.execCommand("Copy");
        textArea.remove();

        swal.fire({
            type: "success",
            text: 'Kopyalandı',
            allowOutsideClick: false,
            showCancelButton: false,
            showConfirmButton:false,
            width: '200px'
        });

        setTimeout(function(){
            swal.close();
        }, 1100);
    }

    function copyToClipboard2(data, message) {
        var textArea = document.createElement("textarea");
        textArea.value = data;
        document.body.appendChild(textArea);
        textArea.select();
        document.execCommand("Copy");
        textArea.remove();

        swal.fire({
            type: "success",
            html: message,
            allowOutsideClick: false,
            showCancelButton: false,
            showConfirmButton:false,
            width: '200px'
        });

        setTimeout(function(){
            swal.close();
        }, 1100);
    }

    function checkSales(element){
        console.log($(element).val())
        if($(element).val().trim().length>0){
            swal.fire({
                text: 'Proses davam edir...',
                allowOutsideClick: false,
                onOpen: function() {
                    swal.showLoading();
                    $.ajax({
                        url: '/sale/sales/check/'+$(element).val(),
                        type: 'GET',
                        dataType: 'json',
                        beforeSend: function() {

                        },
                        success: function(data) {
                            console.log(data);
                            swal.close();
                            swal.fire({
                                title: data.id + ". " + data.customer.person.firstName + " " + data.customer.person.lastName,
                                html: data.action.inventory.name + "<br/>" + data.action.inventory.barcode,
                                type: "info",
                                cancelButtonText: 'Bağla',
                                cancelButtonClass: 'btn btn-info',
                                footer: '<a href>Məlumatlar yenilənsinmi?</a>'
                            });
                        },
                        error: function() {
                            swal.fire({
                                title: "Məlumat tapılmadı!",
                                html: "Satış kodu səhvdir!",
                                type: "error",
                                cancelButtonText: 'Bağla',
                                cancelButtonColor: '#c40000',
                                cancelButtonClass: 'btn btn-danger',
                                footer: '<a href>Məlumatlar yenilənsinmi?</a>'
                            });
                        }
                    })
                }
            });
        }
    }

    $(".modal-header").on("mousedown", function(mousedownEvt) {
        var $draggable = $(this);
        var x = mousedownEvt.pageX - $draggable.offset().left,
            y = mousedownEvt.pageY - $draggable.offset().top;
        $("body").on("mousemove.draggable", function(mousemoveEvt) {
            $draggable.closest(".modal-dialog").offset({
                "left": mousemoveEvt.pageX - x,
                "top": mousemoveEvt.pageY - y
            });
        });
        $("body").one("mouseup", function() {
            $("body").off("mousemove.draggable");
        });
        $draggable.closest(".modal").one("bs.modal.hide", function() {
            $("body").off("mousemove.draggable");
        });
    });
</script>

</body>
</html>
