<%--
  Created by IntelliJ IDEA.
  User: irkan.ahmadov
  Date: 01.09.2019
  Time: 1:22
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="utl" uri="/WEB-INF/tld/Util.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="kt-container  kt-grid__item kt-grid__item--fluid">
    <div class="kt-grid kt-grid--desktop kt-grid--ver kt-grid--ver-desktop kt-app">
        <button class="kt-app__aside-close" id="kt_user_profile_aside_close">
            <i class="la la-close"></i>
        </button>
        <div class="kt-grid__item kt-app__toggle kt-app__aside" id="kt_user_profile_aside">
            <%@include file="profile-aside.jsp" %>
        </div>
        <div class="kt-grid__item kt-grid__item--fluid kt-app__content">
            <div class="row">
                <div class="col-xl-12">
                    <div class="kt-portlet kt-portlet--height-fluid">
                        <div class="kt-portlet__head">
                            <div class="kt-portlet__head-label">
                                <h3 class="kt-portlet__head-title">Şifrəni dəyişdir<small>hesaba daxil olmaq üçün şifrə</small></h3>
                            </div>
                            <div class="kt-portlet__head-toolbar kt-hidden">
                                <div class="kt-portlet__head-toolbar">
                                    <div class="dropdown dropdown-inline">
                                        <button type="button" class="btn btn-clean btn-sm btn-icon btn-icon-md" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            <i class="la la-sellsy"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <form:form modelAttribute="form" id="form" method="post" action="/profile/change-password" cssClass="kt-form kt-form--label-right form-group">
                            <div class="kt-portlet__body">
                                <div class="kt-section kt-section--first">
                                    <div class="kt-section__body">
                                        <div class="alert alert-solid-danger alert-bold fade show kt-margin-t-20 kt-margin-b-40" role="alert">
                                            <div class="alert-icon"><i class="fa fa-exclamation-triangle"></i></div>
                                            <div class="alert-text">Şifrələrin vaxtaşırı yenilənmə funksionallığı nəzərdə tutulmamışdır. Xahiş edilir yeni şifrə daxil edərkən nəzərə alasınız!</div>
                                            <div class="alert-close">
                                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                                    <span aria-hidden="true"><i class="la la-close"></i></span>
                                                </button>
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <form:label path="newPassword" cssClass="col-xl-3 col-lg-3 col-form-label">Yeni şifrə</form:label>
                                            <div class="col-lg-9 col-xl-6">
                                                <form:password path="newPassword" cssClass="form-control" value="" placeholder="Yeni şifrənizi daxil edin"/>
                                            </div>
                                        </div>
                                        <div class="form-group form-group-last row">
                                            <form:label path="verifyPassword" cssClass="col-xl-3 col-lg-3 col-form-label">Şifrənin təkrarı</form:label>
                                            <div class="col-lg-9 col-xl-6">
                                                <form:password path="verifyPassword" cssClass="form-control" value="" placeholder="Yeni şifrənizi təkrar daxil edin"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <c:set var="changePassword" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'change-password')}"/>
                            <c:if test="${changePassword.status}">
                                <div class="kt-portlet__foot">
                                    <div class="kt-form__actions">
                                        <div class="row">
                                            <div class="col-lg-3 col-xl-3">
                                            </div>
                                            <div class="col-lg-9 col-xl-9">
                                                <button type="button" class="btn btn-brand btn-bold" onclick="submit($('#form'))">Şifrəni dəyişdir</button>&nbsp;
                                                <button type="reset" class="btn btn-secondary" onclick="$('#form').reset();">Təmizlə</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $( "#form" ).validate({
        rules: {
            newPassword: {
                required: true,
                minlength: 6
            },
            verifyPassword: {
                required: true,
                equalTo: "#newPassword",
                minlength: 6
            }
        },
        invalidHandler: function(event, validator) {
            KTUtil.scrollTop();
            swal.close();
        },
    });
</script>