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
                    <div class="kt-portlet">
                        <div class="kt-portlet__head">
                            <div class="kt-portlet__head-label">
                                <h3 class="kt-portlet__head-title">Hesab məlumatı <small>hesab sazlamalarını dəyişdirə bilərsiniz</small></h3>
                            </div>
                        </div>
                        <div class="kt-portlet__body">
                            <div class="kt-section kt-section--first">
                                <form:form modelAttribute="form" id="form" method="post" action="/profile/account-information" cssClass="kt-form kt-form--label-right">
                                    <form:hidden path="id"/>
                                    <div class="kt-section__body">
                                        <div class="row">
                                            <label class="col-xl-3"></label>
                                            <div class="col-lg-9 col-xl-6">
                                                <h3 class="kt-section__title kt-section__title-sm">Hesab:</h3>
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-xl-3 col-lg-3 col-form-label">İstifadəçi adı</label>
                                            <div class="col-lg-9 col-xl-6">
                                                <div class="kt-spinner kt-spinner--sm kt-spinner--success kt-spinner--right kt-spinner--input">
                                                    <input class="form-control" type="text" value="<c:out value="${sessionScope.user.username}"/>" readonly/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-xl-3 col-lg-3 col-form-label">Sistemin dili</label>
                                            <div class="col-lg-9 col-xl-6">
                                                <select name="language" class="selectpicker form-control" data-width="fit">
                                                    <c:forEach var="t" items="${languages}" varStatus="loop">
                                                        <c:set var="selected" value="${t.attr1==sessionScope.user.userDetail.language?'selected':''}"/>
                                                        <option value="<c:out value="${t.attr1}"/>" <c:out value="${selected}"/>
                                                                data-content='<span class="flag-icon kt-header__topbar-icon"><img style="height:20px;" src="<c:out value="/assets/media/flags/${t.attr2}" />" alt="<c:out value="${t.attr1}"/>"></span> <c:out value="${t.name}"/>'>
                                                            <c:out value="${t.name}"/>
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group form-group-last row">
                                            <label class="col-xl-3 col-lg-3 col-form-label">Xəbərdarlıqlar</label>
                                            <div class="col-lg-9 col-xl-6">
                                                <div class="kt-checkbox-inline">
                                                    <label class="kt-checkbox kt-checkbox--brand">
                                                        <form:checkbox path="emailNotification"/> Email xəbərdarlıq
                                                        <span></span>
                                                    </label>
                                                    <label class="kt-checkbox kt-checkbox--brand">
                                                        <form:checkbox path="smsNotification"/> Sms xəbərdarlıq
                                                        <span></span>
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </form:form>
                            </div>
                        </div>
                        <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
                        <c:if test="${edit.status}">
                            <div class="kt-portlet__foot">
                                <div class="kt-form__actions">
                                    <div class="row">
                                        <div class="col-lg-3 col-xl-3">
                                        </div>
                                        <div class="col-lg-9 col-xl-9">
                                            <button type="reset" class="btn btn-success" onclick="submit($('#form'))">Yadda saxla</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(function(){
        $('.selectpicker').selectpicker();
    });
</script>