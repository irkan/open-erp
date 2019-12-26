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

<div class="row">
    <div class="col-xl-12">
        <div class="kt-portlet">
            <div class="kt-portlet__head">
                <div class="kt-portlet__head-label">
                    <h3 class="kt-portlet__head-title">Şəxsi məlumat <small>əlaqə məlumatınızı yeniləyə bilərsiniz</small></h3>
                </div>
                <div class="kt-portlet__head-toolbar">
                    <div class="kt-portlet__head-wrapper">
                        <div class="dropdown dropdown-inline">
                            <button type="button" class="btn btn-label-brand btn-sm btn-icon btn-icon-md" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <i class="flaticon2-gear"></i>
                            </button>
                            <div class="dropdown-menu dropdown-menu-right">
                                <ul class="kt-nav">
                                    <li class="kt-nav__item">
                                        <a href="#" class="kt-nav__link">
                                            <i class="kt-nav__link-icon la la-print"></i>
                                            <span class="kt-nav__link-text">Çap edin</span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

                <div class="kt-portlet__body">
                    <div class="kt-section kt-section--first">
                        <div class="kt-section__body">
                            <div class="row">
                                <label class="col-xl-3"></label>
                                <div class="col-lg-9 col-xl-6">
                                    <h3 class="kt-section__title kt-section__title-sm">Şəxsi məlumat:</h3>
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="col-lg-2">
                                    <div class="kt-avatar kt-avatar--outline" id="kt_user_avatar">
                                        <div class="kt-avatar__holder" style="background-image: url('<c:url value="/assets/media/users/default.jpg" />');"></div>
                                        <label class="kt-avatar__upload" data-toggle="kt-tooltip" title="" data-original-title="Change avatar">
                                            <i class="fa fa-pen"></i>
                                            <input type="file" name="profile_avatar" accept=".png, .jpg, .jpeg">
                                        </label>
                                        <span class="kt-avatar__cancel" data-toggle="kt-tooltip" title="" data-original-title="İmnina et - avatar">
                                                    <i class="fa fa-times"></i>
                                                </span>
                                    </div>
                                </div>
                                <div class="col-lg-10">
                                    <div class="form-group row">
                                        <label class="col-xl-2 col-lg-2 col-form-label text-right">Ad</label>
                                        <div class="col-lg-4 col-xl-4">
                                            <input class="form-control" type="text" value="<c:out value="${sessionScope.user.employee.person.firstName}"/>" aria-describedby="basic-addon1" readonly>
                                        </div>
                                        <label class="col-xl-2 col-lg-2 col-form-label text-right">Soyad</label>
                                        <div class="col-lg-4 col-xl-4">
                                            <input class="form-control" type="text" value="<c:out value="${sessionScope.user.employee.person.lastName}"/>" readonly>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-xl-2 col-lg-2 col-form-label text-right">Struktur</label>
                                        <div class="col-lg-4 col-xl-4">
                                            <input class="form-control" type="text" value="<c:out value="${sessionScope.user.employee.organization.name}"/>" readonly>
                                        </div>
                                        <c:if test="${sessionScope.user.employee.person.fatherName!=null}">
                                            <label class="col-xl-2 col-lg-2 col-form-label text-right">Ata adı</label>
                                            <div class="col-lg-4 col-xl-4">
                                                <input class="form-control" type="text" value="${sessionScope.user.employee.person.fatherName}" readonly>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <label class="col-xl-3"></label>
                                <div class="col-lg-9 col-xl-6">
                                    <h3 class="kt-section__title kt-section__title-sm">Əlaqə vasitələri:</h3>
                                </div>
                            </div>
                            <form:form modelAttribute="form" id="form" method="post" action="/route/sub/profile/personal-information" cssClass="kt-form kt-form--label-right">
                                <form:hidden path="id"/>
                                <div class="form-group row">
                                    <form:label path="mobilePhone" cssClass="col-xl-2 col-lg-2 col-form-label">Mobil nömrə</form:label>
                                    <div class="col-lg-4 col-xl-4">
                                        <div class="input-group">
                                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-phone"></i></span></div>
                                            <form:input path="mobilePhone" cssClass="form-control" placeholder="505505050" aria-describedby="basic-addon1" />
                                        </div>
                                        <form:errors path="mobilePhone" cssClass="control-label alert-danger"/>
                                    </div>
                                    <form:label path="homePhone" cssClass="col-xl-2 col-lg-2 col-form-label">Şəhər nömrəsi</form:label>
                                    <div class="col-lg-4 col-xl-4">
                                        <div class="input-group">
                                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-phone"></i></span></div>
                                            <form:input path="homePhone" cssClass="form-control" placeholder="125505050" aria-describedby="basic-addon1" />
                                        </div>
                                        <form:errors path="homePhone" cssClass="control-label alert-danger"/>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <form:label path="email" cssClass="col-xl-2 col-lg-2 col-form-label">Email</form:label>
                                    <div class="col-lg-10 col-xl-10">
                                        <div class="input-group">
                                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-at"></i></span></div>
                                            <form:input path="email" cssClass="form-control" placeholder="example@example.com" aria-describedby="basic-addon1" />
                                        </div>
                                        <form:errors path="email" cssClass="control-label alert-danger"/>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <form:label path="email" cssClass="col-xl-2 col-lg-2 col-form-label">Ünvan</form:label>
                                    <div class="col-lg-7 col-xl-7">
                                        <div class="input-group">
                                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-map-marker"></i></span></div>
                                            <form:input path="address" cssClass="form-control" placeholder="" aria-describedby="basic-addon1" readonly="true" />
                                        </div>
                                        <form:errors path="address" cssClass="control-label alert-danger"/>
                                    </div>
                                    <div class="col-lg-3 col-xl-3">
                                        <div class="input-group">
                                            <form:input path="city.name" cssClass="form-control" aria-describedby="basic-addon1" readonly="true" />
                                            <form:hidden path="city.id" cssClass="form-control" aria-describedby="basic-addon1" readonly="true" />
                                        </div>
                                    </div>
                                </div>
                            </form:form>
                        </div>
                    </div>

                </div>
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
        </div>
    </div>
</div>