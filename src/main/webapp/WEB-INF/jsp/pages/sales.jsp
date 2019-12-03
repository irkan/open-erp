<%--
  Created by IntelliJ IDEA.
  User: irkan.ahmadov
  Date: 20.09.2019
  Time: 1:22
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="utl" uri="/WEB-INF/tld/Util.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="<c:url value="/assets/css/demo4/pages/wizard/wizard-1.css" />" rel="stylesheet" type="text/css"/>
<div class="kt-container  kt-grid__item kt-grid__item--fluid">
    <div class="row">
        <div class="col-lg-12">
            <div class="kt-portlet kt-portlet--mobile">
                <div class="kt-portlet__body">
                    <c:choose>
                        <c:when test="${not empty list}">
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="group_table">
                                <thead>
                                <tr>
                                    <th>Əməliyyat</th>
                                    <th>Kod</th>
                                    <th>Satış tarixi</th>
                                    <th>İnventar</th>
                                    <th>Müştəri</th>
                                    <th>Qiymət</th>
                                    <th>Son qiymət</th>
                                    <th>İlkin ödəniş</th>
                                    <th>Qrafik</th>
                                    <th>Period</th>
                                    <th>Endirim</th>
                                    <th>Endirim səbəbi</th>
                                    <th>Zəmanət müddəti</th>
                                    <th>Zəmanət bitir</th>
                                    <th>Ödəniş</th>
                                    <th>Satış komandası</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list}" varStatus="loop">
                                    <tr data="<c:out value="${utl:toJson(t)}" />">
                                        <td nowrap class="text-center">
                                            <c:set var="view" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'view')}"/>
                                            <c:choose>
                                                <c:when test="${view.status}">
                                                    <a href="/collect/payment-regulator-note/<c:out value="${t.payment.id}"/>" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="Qeydlər">
                                                        <i class="la <c:out value="${view.object.icon}"/>"></i>
                                                    </a>
                                                </c:when>
                                            </c:choose>
                                            <c:set var="detail" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'detail')}"/>
                                            <c:choose>
                                                <c:when test="${detail.status}">
                                                    <a href="/collect/payment-regulator-detail/<c:out value="${t.payment.id}"/>" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${detail.object.name}"/>">
                                                        <i class="la <c:out value="${detail.object.icon}"/>"></i>
                                                    </a>
                                                </c:when>
                                            </c:choose>
                                            <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
                                            <c:choose>
                                                <c:when test="${edit.status}">
                                                    <a href="javascript:edit($('#kt_form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${edit.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${edit.object.name}"/>">
                                                        <i class="<c:out value="${edit.object.icon}"/>"></i>
                                                    </a>
                                                </c:when>
                                            </c:choose>
                                            <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
                                            <c:choose>
                                                <c:when test="${delete.status}">
                                                    <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.action.inventory.name}" /> <br/> <c:out value="${t.customer.person.fullName}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
                                                        <i class="<c:out value="${delete.object.icon}"/>"></i>
                                                    </a>
                                                </c:when>
                                            </c:choose>
                                            <c:set var="export" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'export')}"/>
                                            <c:choose>
                                                <c:when test="${export.status}">
                                                    <a href="javascript:exportContract($('#form-export-contract'), '<c:out value="${t.id}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="Hesab-fakturanın çapı">
                                                        <i class="<c:out value="${export.object.icon}"/>"></i>
                                                    </a>
                                                </c:when>
                                            </c:choose>
                                        </td>
                                        <td><c:out value="${t.id}" /></td>
                                        <td><fmt:formatDate value = "${t.saleDate}" pattern = "dd.MM.yyyy" /></td>
                                        <th>
                                            <c:out value="${t.action.inventory.name}" /><br/>
                                            <c:out value="${t.action.inventory.barcode}" /><br/>
                                            <c:out value="${t.action.warehouse.name}" />
                                        </th>
                                        <th>
                                            <c:out value="${t.customer.person.fullName}" /><br/>
                                            Müştəri kodu: <c:out value="${t.customer.id}" />
                                            <c:if test="${not empty t.customer.person.contact.email}">
                                                <c:out value="${t.customer.person.contact.email}" /><br/>
                                            </c:if>
                                            <c:if test="${not empty t.customer.person.contact.mobilePhone}">
                                                <c:out value="${t.customer.person.contact.mobilePhone}" />&nbsp;
                                            </c:if>
                                            <c:if test="${not empty t.customer.person.contact.homePhone}">
                                                <c:out value="${t.customer.person.contact.homePhone}" />
                                            </c:if>
                                            <c:if test="${not empty t.customer.person.contact.relationalPhoneNumber1}">
                                                <c:out value="${t.customer.person.contact.relationalPhoneNumber1}" />
                                            </c:if>
                                            <c:if test="${not empty t.customer.person.contact.relationalPhoneNumber2}">
                                                <c:out value="${t.customer.person.contact.relationalPhoneNumber2}" />
                                            </c:if>
                                            <c:if test="${not empty t.customer.person.contact.relationalPhoneNumber2}">
                                                <c:out value="${t.customer.person.contact.relationalPhoneNumber2}" />
                                            </c:if><br/>
                                            <c:if test="${not empty t.customer.person.contact.address}">
                                                <c:out value="${t.customer.person.contact.city.name}" />,&nbsp;
                                                <c:out value="${t.customer.person.contact.address}" /><br/>
                                            </c:if>
                                            <c:if test="${not empty t.customer.person.contact.livingAddress}">
                                                <c:out value="${t.customer.person.contact.livingCity.name}" />,&nbsp;
                                                <c:out value="${t.customer.person.contact.livingAddress}" />
                                            </c:if>
                                        </th>
                                        <th><c:out value="${t.payment.price}" /></th>
                                        <th><c:out value="${t.payment.lastPrice}" /></th>
                                        <td><c:out value="${t.payment.down}" /></td>
                                        <td><c:out value="${t.payment.schedule.name}" /></td>
                                        <td><c:out value="${t.payment.period.name}" /></td>
                                        <td><c:out value="${t.payment.discount}" /></td>
                                        <td><c:out value="${t.payment.description}" /></td>
                                        <td><c:out value="${t.guarantee}" /> ay</td>
                                        <td><fmt:formatDate value = "${t.guaranteeExpire}" pattern = "dd.MM.yyyy" /></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${t.payment.cash}">
                                                    <span class="kt-font-bold kt-font-success">Nəğd</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="kt-font-bold kt-font-danger">Kredit</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            Konsul: <c:out value="${t.console.person.fullName}" /><br/>
                                            Ven lider: <c:out value="${t.vanLeader.person.fullName}" /><br/>
                                            Diller: <c:out value="${t.dealer.person.fullName}" /><br/>
                                            Canvasser: <c:out value="${t.canavasser.person.fullName}" />
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </c:when>
                        <c:otherwise>
                            <div class="row">
                                <div class="col-md-12 text-center" style="letter-spacing: 10px;">
                                    Məlumat yoxdur
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modal-operation" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body" style="padding: 0;">

            </div>
            <%--<div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#form'));">Yadda saxla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>--%>


            <div class="kt-grid  kt-wizard-v1 kt-wizard-v1--white" id="kt_wizard_v1" data-ktwizard-state="step-first">
                <div class="kt-grid__item">

                    <!--begin: Form Wizard Nav -->
                    <div class="kt-wizard-v1__nav">
                        <div class="kt-wizard-v1__nav-items">
                            <div class="kt-wizard-v1__nav-item" data-ktwizard-type="step" data-ktwizard-state="current">
                                <div class="kt-wizard-v1__nav-body">
                                    <div class="kt-wizard-v1__nav-icon">
                                        <i class="flaticon-list"></i>
                                    </div>
                                    <div class="kt-wizard-v1__nav-label">
                                        1. Müştəri
                                    </div>
                                </div>
                            </div>
                            <div class="kt-wizard-v1__nav-item" data-ktwizard-type="step">
                                <div class="kt-wizard-v1__nav-body">
                                    <div class="kt-wizard-v1__nav-icon">
                                        <i class="flaticon-bus-stop"></i>
                                    </div>
                                    <div class="kt-wizard-v1__nav-label">
                                        2. Əlaqə
                                    </div>
                                </div>
                            </div>
                            <div class="kt-wizard-v1__nav-item" data-ktwizard-type="step">
                                <div class="kt-wizard-v1__nav-body">
                                    <div class="kt-wizard-v1__nav-icon">
                                        <i class="flaticon-responsive"></i>
                                    </div>
                                    <div class="kt-wizard-v1__nav-label">
                                        3. İnventar
                                    </div>
                                </div>
                            </div>
                            <div class="kt-wizard-v1__nav-item" data-ktwizard-type="step">
                                <div class="kt-wizard-v1__nav-body">
                                    <div class="kt-wizard-v1__nav-icon">
                                        <i class="flaticon-price-tag"></i>
                                    </div>
                                    <div class="kt-wizard-v1__nav-label">
                                        4. Ödəmə
                                    </div>
                                </div>
                            </div>
                            <div class="kt-wizard-v1__nav-item" data-ktwizard-type="step">
                                <div class="kt-wizard-v1__nav-body">
                                    <div class="kt-wizard-v1__nav-icon">
                                        <i class="flaticon-truck"></i>
                                    </div>
                                    <div class="kt-wizard-v1__nav-label">
                                        5. Satıcı
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!--end: Form Wizard Nav -->
                </div>
                <div class="kt-grid__item kt-grid__item--fluid kt-wizard-v1__wrapper">
                <form:form modelAttribute="form" id="kt_form" method="post" action="/sale/sales" cssClass="form-group kt-form">
                    <form:input path="id" type="hidden"/>
                        <div class="kt-wizard-v1__content" data-ktwizard-type="step-content" data-ktwizard-state="current">
                            <div class="kt-form__section kt-form__section--first">
                                <div class="kt-wizard-v1__form">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <form:label path="customer">Müştəri kodu</form:label>
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text" style="background-color: white; border-right: none;"><i class="la la-search"></i></span>
                                                    </div>
                                                    <form:input path="customer" autocomplete="false" class="form-control" placeholder="Müştəri kodunu daxil edin..." style="border-left: none;" />
                                                    <div class="input-group-append">
                                                        <button class="btn btn-primary" type="button" onclick="findCustomer($('input[name=\'customer\']'))">Müştərini axtar</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-4 offset-md-2">
                                            <div class="form-group">
                                                <form:label path="saleDate">Satış tarixi</form:label>
                                                <div class="input-group date" >
                                                    <form:input path="saleDate" cssClass="form-control datepicker-element" date="date" placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="saleDate" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <form:label path="customer.person.firstName">Ad</form:label>
                                                <form:input path="customer.person.firstName" cssClass="form-control" placeholder="Adı daxil edin Məs: Səbuhi"/>
                                                <form:errors path="customer.person.firstName" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <form:label path="customer.person.lastName">Soyad</form:label>
                                                <form:input path="customer.person.lastName" cssClass="form-control" placeholder="Soyadı daxil edin Məs: Vəliyev"/>
                                                <form:errors path="customer.person.lastName" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <form:label path="customer.person.fatherName">Ata adı</form:label>
                                                <form:input path="customer.person.fatherName" cssClass="form-control" placeholder="Ata adını daxil edin"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <form:label path="customer.person.birthday">Doğum tarixi</form:label>
                                                <div class="input-group date" >
                                                    <form:input path="customer.person.birthday" cssClass="form-control datepicker-element" date="date" placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="customer.person.birthday" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <form:label path="customer.person.idCardSerialNumber">Ş.v - nin seriya nömrəsi</form:label>
                                                <form:input path="customer.person.idCardSerialNumber" cssClass="form-control" placeholder="AA0822304"/>
                                                <form:errors path="customer.person.idCardSerialNumber" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <form:label path="customer.person.idCardPinCode">Ş.v - nin pin kodu</form:label>
                                                <form:input path="customer.person.idCardPinCode" cssClass="form-control" placeholder="Məs: 4HWL0AM"/>
                                                <form:errors path="customer.person.idCardPinCode" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <form:label path="customer.person.voen">VÖEN</form:label>
                                                <form:input path="customer.person.voen" cssClass="form-control" placeholder="Məs: 0000000000"/>
                                                <form:errors path="customer.person.voen" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label>Ş.v-nin ön hissəsi</label>
                                                <div></div>
                                                <div class="custom-file">
                                                    <input type="file" name="file" class="custom-file-input" id="file1" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
                                                    <label class="custom-file-label" for="file1">Ş.v-nin ön hissəsi</label>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label>Ş.v-nin arxa hissəsi</label>
                                                <div></div>
                                                <div class="custom-file">
                                                    <input type="file" name="file" class="custom-file-input" id="file2" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
                                                    <label class="custom-file-label" for="file2">Ş.v-nin arxa hissəsi</label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="kt-wizard-v1__content" data-ktwizard-type="step-content">
                            <div class="kt-form__section kt-form__section--first">
                                <div class="kt-wizard-v1__form">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.mobilePhone">Mobil nömrə</form:label>
                                                <div class="input-group" >
                                                    <form:input path="customer.person.contact.mobilePhone" cssClass="form-control" placeholder="0505505550"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-phone"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="customer.person.contact.mobilePhone" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.homePhone">Şəhər nömrəsi</form:label>
                                                <div class="input-group" >
                                                    <form:input path="customer.person.contact.homePhone" cssClass="form-control" placeholder="0124555050"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-phone"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="customer.person.contact.homePhone" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.email">Email</form:label>
                                                <div class="input-group" >
                                                    <form:input path="customer.person.contact.email" cssClass="form-control" placeholder="example@example.com"/>
                                                    <div class="input-group-append">
                                                            <span class="input-group-text">
                                                                <i class="la la-at"></i>
                                                            </span>
                                                    </div>
                                                </div>
                                                <form:errors path="customer.person.contact.email" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.relationalPhoneNumber1">Əlaqəli şəxs nömrəsi #1</form:label>
                                                <div class="input-group" >
                                                    <form:input path="customer.person.contact.relationalPhoneNumber1" cssClass="form-control" placeholder="0505505550"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-phone"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="customer.person.contact.relationalPhoneNumber1" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.relationalPhoneNumber2">Əlaqəli şəxs nömrəsi #2</form:label>
                                                <div class="input-group" >
                                                    <form:input path="customer.person.contact.relationalPhoneNumber2" cssClass="form-control" placeholder="0505505550"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-phone"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="customer.person.contact.relationalPhoneNumber2" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.relationalPhoneNumber3">Əlaqəli şəxs nömrəsi #3</form:label>
                                                <div class="input-group" >
                                                    <form:input path="customer.person.contact.relationalPhoneNumber3" cssClass="form-control" placeholder="0505505550"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-phone"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="customer.person.contact.relationalPhoneNumber3" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.city">Şəhər</form:label>
                                                <form:select  path="customer.person.contact.city" cssClass="custom-select form-control">
                                                    <form:options items="${cities}" itemLabel="name" itemValue="id" />
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-9">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.address">Ünvan</form:label>
                                                <div class="input-group" >
                                                    <div class="input-group-append">
                                                        <span class="input-group-text">
                                                            <i class="la la-street-view"></i>
                                                        </span>
                                                    </div>
                                                    <form:input path="customer.person.contact.address" cssClass="form-control" placeholder="Küçə adı, ev nömrəsi və s."/>
                                                </div>
                                                <form:errors path="customer.person.contact.address" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.livingCity">Yaşadığı şəhər</form:label>
                                                <form:select  path="customer.person.contact.livingCity" cssClass="custom-select form-control">
                                                    <form:options items="${cities}" itemLabel="name" itemValue="id" />
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-9">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.livingAddress">Yaşayış ünvanı</form:label>
                                                <div class="input-group" >
                                                    <div class="input-group-append">
                                                        <span class="input-group-text">
                                                            <i class="la la-street-view"></i>
                                                        </span>
                                                    </div>
                                                    <form:input path="customer.person.contact.livingAddress" cssClass="form-control" placeholder="Küçə adı, ev nömrəsi və s."/>
                                                </div>
                                                <form:errors path="customer.person.contact.livingAddress" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="kt-wizard-v1__content" data-ktwizard-type="step-content">
                            <div class="kt-form__section kt-form__section--first">
                                <div class="kt-wizard-v1__form">
                                    <div class="row">
                                        <div class="col-sm-8 offset-sm-2">
                                            <form:hidden path="action" cssClass="form-control"/>
                                            <div class="form-group">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text" style="background-color: white; border-right: none;"><i class="la la-search"></i></span>
                                                    </div>
                                                    <form:input path="action.inventory.barcode" class="form-control" placeholder="Barkodu daxil edin..." style="border-left: none;" />
                                                    <div class="input-group-append">
                                                        <button class="btn btn-primary" type="button" onclick="findInventory($('input[name=\'action.inventory.barcode\']'))">İnventar axtar</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <form:label path="action.inventory.name">İnventar</form:label>
                                                <form:input path="action.inventory.name" cssClass="form-control" readonly="true"/>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <form:label path="action.warehouse.name">Anbar</form:label>
                                                <form:input path="action.warehouse.name" cssClass="form-control" readonly="true"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <form:label path="action.inventory.description">Açıqlama</form:label>
                                        <form:textarea path="action.inventory.description" cssClass="form-control" readonly="true"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="kt-wizard-v1__content" data-ktwizard-type="step-content">
                            <div class="kt-form__section kt-form__section--first">
                                <div class="kt-wizard-v1__form">
                                    <div class="row">
                                        <div class="col-sm-4 offset-sm-1">
                                            <form:label path="payment.price">Qiymət</form:label>
                                            <form:select  path="payment.price" onchange="calculate($(this))" cssClass="custom-select form-control">
                                                <form:options items="${sale_prices}" itemLabel="name" itemValue="attr1" />
                                            </form:select>
                                            <form:errors path="payment.price" cssClass="control-label alert-danger"/>
                                        </div>
                                        <div class="col-sm-3 text-center">
                                            <div class="form-group mt-3 pt-4">
                                                <label class="kt-checkbox kt-checkbox--brand">
                                                    <form:checkbox path="payment.cash" onclick="doCash($(this), '10%')"/> Ödəniş nağd dırmı?
                                                    <span></span>
                                                </label>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <form:label path="guarantee">Zəmanət müddəti</form:label>
                                            <form:select  path="guarantee" cssClass="custom-select form-control">
                                                <form:options items="${guarantees}" itemLabel="name" itemValue="attr1" />
                                            </form:select>
                                            <form:errors path="guarantee" cssClass="control-label alert-danger"/>
                                        </div>
                                    </div>
                                    <div class="row kt-hidden animated zoomIn" id="cash-div">
                                        <div class="col-md-5 offset-md-1">
                                            <div class="form-group">
                                                <form:label path="payment.discount">Endirim dəyəri</form:label>
                                                <form:input path="payment.discount" cssClass="form-control" readonly="true" cssStyle="text-align: -webkit-center; text-align: center; font-weight: bold; letter-spacing: 3px;"/>
                                                <form:errors path="payment.discount" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-5">
                                            <div class="form-group">
                                                <form:label path="payment.description">Açıqlama</form:label>
                                                <form:input path="payment.description" cssClass="form-control" readonly="true"/>
                                                <form:errors path="payment.description" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-10 offset-md-1">
                                            <div class="alert alert-info alert-elevate" role="alert" style="padding: 0; padding-left: 1rem; padding-right: 1rem; padding-top: 5px;">
                                                <div class="alert-icon"><i class="flaticon-warning kt-font-brand kt-font-light"></i></div>
                                                <div class="alert-text text-center">
                                                    <div style="font-size: 18px; font-weight: bold; letter-spacing: 2px;">
                                                        Yekun ödəniləcək məbləğ:
                                                        <span id="lastPriceLabel">0</span>
                                                        <span> AZN</span>
                                                        <form:hidden path="payment.lastPrice"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row animated zoomIn" id="credit-div">
                                        <div class="col-sm-7 offset-sm-1">
                                            <div class="row">
                                                <div class="col-sm-4">
                                                    <div class="form-group">
                                                        <form:label path="payment.down">İlkin ödəniş</form:label>
                                                        <div class="input-group" >
                                                            <form:input path="payment.down" cssClass="form-control" placeholder="İlkin ödənişi daxil edin"/>
                                                            <div class="input-group-append">
                                                    <span class="input-group-text">
                                                        <i class="la la-usd"></i>
                                                    </span>
                                                            </div>
                                                        </div>
                                                        <form:errors path="payment.down" cssClass="alert-danger control-label"/>
                                                    </div>
                                                </div>
                                                <div class="col-sm-4">
                                                    <div class="form-group">
                                                        <form:label path="payment.schedule">Ödəniş qrafiki</form:label>
                                                        <form:select  path="payment.schedule" cssClass="custom-select form-control">
                                                            <form:options items="${payment_schedules}" itemLabel="name" itemValue="id" />
                                                        </form:select>
                                                        <form:errors path="payment.schedule" cssClass="control-label alert-danger"/>
                                                    </div>
                                                </div>
                                                <div class="col-sm-4">
                                                    <div class="form-group">
                                                        <form:label path="payment.period">Ödəniş edilsin</form:label>
                                                        <form:select  path="payment.period" cssClass="custom-select form-control">
                                                            <form:options items="${payment_periods}" itemLabel="name" itemValue="id" />
                                                        </form:select>
                                                        <form:errors path="payment.period" cssClass="control-label alert-danger"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-3 text-center">
                                            <button type="button" class="btn btn-outline-info btn-tallest" style="font-size: 16px;padding-left: 7px; padding-right: 8px;" onclick="schedule($('input[name=\'payment.lastPrice\']'), $('input[name=\'payment.down\']'), $('select[name=\'payment.schedule\']'), $('select[name=\'payment.period\']'), $('input[name=\'saleDate\']'))"><i class="fa fa-play"></i> Ödəniş qrafiki yarat</button>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-10 offset-sm-1" id="schedule-div">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="kt-wizard-v1__content" data-ktwizard-type="step-content">
                            <div class="kt-form__section kt-form__section--first">
                                <div class="kt-wizard-v1__review">
                                    <div class="kt-wizard-v1__review-item">
                                        <div class="row">
                                            <div class="col-md-3 offset-md-1 text-right">
                                                <div class="kt-wizard-v1__review-title p-2">
                                                    Konsul
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <form:select  path="console" cssClass="custom-select form-control select2-single" multiple="single">
                                                        <c:forEach var="itemGroup" items="${employees}" varStatus="itemGroupIndex">
                                                            <optgroup label="${itemGroup.key}">
                                                                <form:options items="${itemGroup.value}" itemLabel="person.fullName" itemValue="id"/>
                                                            </optgroup>
                                                        </c:forEach>
                                                    </form:select>
                                                    <form:errors path="console" cssClass="control-label alert-danger"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="kt-separator kt-separator--border-dashed kt-separator--space-sm kt-separator--portlet-fit" style="margin: 1rem 0"></div>
                            <div class="kt-form__section kt-form__section--first">
                                <div class="kt-wizard-v1__review">
                                    <div class="kt-wizard-v1__review-item">
                                        <div class="row">
                                            <div class="col-md-3 offset-md-1 text-right">
                                                <div class="kt-wizard-v1__review-title p-2">
                                                    Ven lider
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <form:select  path="vanLeader" cssClass="custom-select form-control select2-single" multiple="single">
                                                        <c:forEach var="itemGroup" items="${employees}" varStatus="itemGroupIndex">
                                                            <optgroup label="${itemGroup.key}">
                                                                <form:options items="${itemGroup.value}" itemLabel="person.fullName" itemValue="id"/>
                                                            </optgroup>
                                                        </c:forEach>
                                                    </form:select>
                                                    <form:errors path="vanLeader" cssClass="control-label alert-danger"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="kt-separator kt-separator--border-dashed kt-separator--space-sm kt-separator--portlet-fit" style="margin: 1rem 0"></div>
                            <div class="kt-form__section kt-form__section--first">
                                <div class="kt-wizard-v1__review">
                                    <div class="kt-wizard-v1__review-item">
                                        <div class="row">
                                            <div class="col-md-3 offset-md-1 text-right">
                                                <div class="kt-wizard-v1__review-title p-2">
                                                    Diller
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <form:select  path="dealer" cssClass="custom-select form-control select2-single" multiple="single">
                                                        <c:forEach var="itemGroup" items="${employees}" varStatus="itemGroupIndex">
                                                            <optgroup label="${itemGroup.key}">
                                                                <form:options items="${itemGroup.value}" itemLabel="person.fullName" itemValue="id"/>
                                                            </optgroup>
                                                        </c:forEach>
                                                    </form:select>
                                                    <form:errors path="dealer" cssClass="control-label alert-danger"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="kt-separator kt-separator--border-dashed kt-separator--space-sm kt-separator--portlet-fit" style="margin: 1rem 0"></div>
                            <div class="kt-form__section kt-form__section--first">
                                <div class="kt-wizard-v1__review">
                                    <div class="kt-wizard-v1__review-item">
                                        <div class="row">
                                            <div class="col-md-3 offset-md-1 text-right">
                                                <div class="kt-wizard-v1__review-title p-2">
                                                    Canvasser
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <form:select  path="canavasser" cssClass="custom-select form-control select2-single" multiple="single">
                                                        <c:forEach var="itemGroup" items="${employees}" varStatus="itemGroupIndex">
                                                            <optgroup label="${itemGroup.key}">
                                                                <form:options items="${itemGroup.value}" itemLabel="person.fullName" itemValue="id"/>
                                                            </optgroup>
                                                        </c:forEach>
                                                    </form:select>
                                                    <form:errors path="canavasser" cssClass="control-label alert-danger"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="kt-form__actions">
                            <button class="btn btn-secondary btn-md btn-tall btn-wide kt-font-bold kt-font-transform-u" data-ktwizard-type="action-prev">
                                Geri
                            </button>
                            <button class="btn btn-success btn-md btn-tall btn-wide kt-font-bold kt-font-transform-u" data-ktwizard-type="action-submit">
                                Yadda saxla
                            </button>
                            <button class="btn btn-brand btn-md btn-tall btn-wide kt-font-bold kt-font-transform-u" data-ktwizard-type="action-next">
                                İrəli
                            </button>
                        </div>
                    </form:form>
                </div>
            </div>

        </div>
    </div>
</div>

<form id="form-export-contract" method="post" action="/export/sale/contract" style="display: none">
    <input type="hidden" name="data" />
</form>

<script src="<c:url value="/assets/js/demo4/pages/crud/datatables/advanced/row-grouping.js" />" type="text/javascript"></script>

<script>
    $('.select2-single').select2({
        placeholder: "Əməkdaşı seçin",
        allowClear: true
    });

    function schedule(lastPrice, down, schedule, period, saleDate){
        var table='';
        swal.fire({
            text: 'Proses davam edir...',
            allowOutsideClick: false,
            onOpen: function() {
                swal.showLoading();
                $.ajax({
                    url: '/sale/payment/schedule/' + $(lastPrice).val() + '/' + $(down).val() + '/' + $(schedule).val() + '/' + $(period).val() + '/' + $(saleDate).val(),
                    type: 'GET',
                    dataType: 'json',
                    beforeSend: function() {
                        table+="<table class='table table-striped- table-bordered table-hover table-checkable  animated zoomIn' id='schedule-table'><thead><th style='width: 20px;' class='text-center'>№</th><th>Ödəniş tarixi</th><th>Ödəniş məbləği</th></thead>"
                    },
                    success: function(data) {
                        table+="<tbody>";
                        $.each(data, function(k, v) {
                            table+="<tr>" +
                                "<th>"+(parseInt(k)+1)+"</th>" +
                                "<th>" +
                                "<input type='hidden' name='payment.schedules["+parseInt(k)+"].scheduleDate'  value='"+v.scheduleDate.split(' ')[0]+"'/> "+
                                v.scheduleDate.split(' ')[0]+
                                "</th>" +
                                "<th>" +
                                "<input type='hidden' name='payment.schedules["+parseInt(k)+"].amount' value='"+v.amount+"' />"+
                                v.amount+
                                " <i style='font-style: italic; font-size: 10px;'>AZN<i></th></tr>";
                            console.log(k + ' - ' + v)
                        });
                        table+="</tbody>";
                        swal.close();
                    },
                    error: function() {
                        swal.fire({
                            title: "Xəta baş verdi!",
                            html: "Cədvəl yarana bilmədi!",
                            type: "error",
                            cancelButtonText: 'Bağla',
                            cancelButtonColor: '#c40000',
                            cancelButtonClass: 'btn btn-danger',
                            footer: '<a href>Məlumatlar yenilənsinmi?</a>'
                        });
                    },
                    complete: function(){
                        table+="</table>";
                        $("#schedule-div").html(table);
                        $("#schedule-table").DataTable({pageLength: 50});
                    }
                })
            }
        });

    }
    function calculate(element){
        var price = $(element).val();
        var discount = $("input[name='payment.discount']").val();
        if(discount.trim().length>0){
            var discounts = discount.trim().split("%");
            if(discounts.length>1){
                price = price-price*parseFloat(discounts[0])*0.01;
            } else {
                price = price-parseFloat(discounts[0]);
            }
            price = Math.ceil(price);
        }
        $("#lastPriceLabel").text(price);
        $("input[name='payment.lastPrice']").val(price);
    }

    $(function(){
        $("select[name='payment.price']").change();
    })

    function doCash(element, defaultCash){
        if($(element).is(":checked")){
            $("#cash-div").removeClass("kt-hidden");
            $("#credit-div").addClass("kt-hidden");
            $("#schedule-div").html('');
            swal.fire({
                title: 'Endirimi təsdiq edirsinizmi?',
                html: 'Endirim faiz və ya məbləğini daxil edin',
                type: 'question',
                allowEnterKey: true,
                showCancelButton: true,
                buttonsStyling: false,
                cancelButtonText: 'Xeyr, edilməsin!',
                cancelButtonClass: 'btn btn-danger',
                confirmButtonText: 'Bəli, edilsin!',
                confirmButtonClass: 'btn btn-success',
                reverseButtons: true,
                allowOutsideClick: false,
                input: 'text',
                inputPlaceholder: 'Buraya daxil edin...',
                inputValue: defaultCash,
                inputAttributes: {
                    maxlength: 10,
                    autocapitalize: 'off',
                    autocorrect: 'off',
                    id: 'sale-value',
                    style: 'text-align: -webkit-center; text-align: center; font-weight: bold; letter-spacing: 3px;'
                },
                footer: '<a href>Məlumatlar yenilənsinmi?</a>'
            }).then(function(result) {
                $("input[name='payment.discount']").val('');
                $("input[name='payment.description']").val('');
                if (result.value) {
                    $("input[name='payment.discount']").val($('#sale-value').val());
                    $("select[name='payment.price']").change();
                    if($('#sale-value').val()!==defaultCash){
                        swal.fire({
                            title: $('#sale-value').val()+' - Səbəbini daxil edin',
                            type: 'info',
                            allowEnterKey: true,
                            buttonsStyling: false,
                            confirmButtonText: 'Təsdiq edirəm',
                            confirmButtonClass: 'btn btn-default',
                            allowOutsideClick: false,
                            input: 'textarea',
                            inputPlaceholder: 'Buraya daxil edin...',
                            inputAttributes: {
                                autocapitalize: 'off',
                                autocorrect: 'off',
                                style: 'letter-spacing: 1px;',
                                'aria-label': 'Type your message here'
                            },
                            footer: '<a href>Məlumatlar yenilənsinmi?</a>'
                        }).then(function(result2){
                            if(result2.value.length>0){
                                $("input[name='payment.description']").val(result2.value);
                            }
                        })

                    }
                }
            })

        } else {
            var price = $("select[name='payment.price']").val();
            $("#lastPriceLabel").text(price);
            $("input[name='payment.lastPrice']").val(price);
            $("#cash-div").addClass("kt-hidden");
            $("#credit-div").removeClass("kt-hidden");
        }
    }


    $('.custom-file-input').on('change', function() {
        var fileName = $(this).val();
        $(this).next('.custom-file-label').addClass("selected").html(fileName);
    });

    var KTWizard1 = function () {
        var wizardEl;
        var formEl;
        var validator;
        var wizard;

        var initWizard = function () {
            wizard = new KTWizard('kt_wizard_v1', {
                startStep: 1
            });

            wizard.on('beforeNext', function(wizardObj) {
                if (validator.form() !== true) {
                    wizardObj.stop();
                }
            });

            wizard.on('beforePrev', function(wizardObj) {
                if (validator.form() !== true) {
                    wizardObj.stop();
                }
            });

            wizard.on('change', function(wizard) {
                setTimeout(function() {
                    KTUtil.scrollTop();
                }, 500);
            });
        }

        var initValidation = function() {
            validator = formEl.validate({
                ignore: ":hidden",
                rules: {
                    /*'customer.person.firstName': {
                        required: true
                    },
                    'customer.person.lastName': {
                        required: true
                    },
                    'customer.person.contact.mobilePhone': {
                        required: true
                    },
                    'customer.person.contact.address': {
                        required: true
                    },
                    'action.inventory.barcode': {
                        required: true
                    },
                    'action.inventory.name': {
                        required: true
                    },
                    'action.warehouse.name': {
                        required: true
                    },
                    'payment.price': {
                        required: true
                    },
                    height: {
                        required: true
                    },
                    length: {
                        required: true
                    },

                    delivery: {
                        required: true
                    },
                    packaging: {
                        required: true
                    },
                    preferreddelivery: {
                        required: true
                    },

                    locaddress1: {
                        required: true
                    },
                    locpostcode: {
                        required: true
                    },
                    loccity: {
                        required: true
                    },
                    locstate: {
                        required: true
                    },
                    loccountry: {
                        required: true
                    }*/
                },
                invalidHandler: function(event, validator) {
                    KTUtil.scrollTop();

                    swal.fire({
                        "title": "",
                        "text": "Məlumatı daxil edin!",
                        "type": "error",
                        "confirmButtonClass": "btn btn-secondary"
                    });
                },
                submitHandler: function (form) {

                }
            });
        }

        var initSubmit = function() {
            var btn = formEl.find('[data-ktwizard-type="action-submit"]');

            btn.on('click', function(e) {
                e.preventDefault();

                if (validator.form()) {
                    KTApp.progress(btn);
                    formEl.ajaxSubmit({
                        success: function() {
                            KTApp.unprogress(btn);
                            swal.fire({
                                "title": "",
                                "text": "Əməliyyat uğurla yerinə yetirildi!",
                                "type": "success",
                                "confirmButtonClass": "btn btn-secondary",
                                onOpen: function(){
                                    $('#modal-operation').modal('toggle');
                                }
                            })
                        }
                    });
                }
            });
        }

        return {
            init: function() {
                wizardEl = KTUtil.get('kt_wizard_v1');
                formEl = $('#kt_form');
                initWizard();
                initValidation();
                initSubmit();
            }
        };
    }();

    jQuery(document).ready(function() {
        KTWizard1.init();
    });

    function findCustomer(element){
        if($(element).val().trim().length>0){
            swal.fire({
                text: 'Proses davam edir...',
                allowOutsideClick: false,
                onOpen: function() {
                    swal.showLoading();
                    $.ajax({
                        url: '/crm/customer/'+$(element).val(),
                        type: 'GET',
                        dataType: 'json',
                        beforeSend: function() {
                            $("input[name='customer.person.firstName']").val('');
                            $("input[name='customer.person.lastName']").val('');
                            $("input[name='customer.person.fatherName']").val('');
                            $("input[name='customer.person.birthday']").val('');
                            $("input[name='customer.person.idCardSerialNumber']").val('');
                            $("input[name='customer.person.idCardPinCode']").val('');
                        },
                        success: function(customer) {
                            console.log(customer);
                            $("input[name='customer.person.firstName']").val(customer.person.firstName);
                            $("input[name='customer.person.lastName']").val(customer.person.lastName);
                            $("input[name='customer.person.fatherName']").val(customer.person.fatherName);
                            $("input[name='customer.person.birthday']").val(customer.person.birthday);
                            $("input[name='customer.person.idCardSerialNumber']").val(customer.person.idCardSerialNumber);
                            $("input[name='customer.person.idCardPinCode']").val(customer.person.idCardPinCode);
                            swal.close();
                        },
                        error: function() {
                            swal.fire({
                                title: "Müştəri tapılmadı!",
                                html: "Müştəri kodunun doğruluğunu yoxlayın.",
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

    function findInventory(element){
        if($(element).val().trim().length>0){
            swal.fire({
                text: 'Proses davam edir...',
                allowOutsideClick: false,
                onOpen: function() {
                    swal.showLoading();
                    $.ajax({
                        url: '/warehouse/inventory/action/'+$(element).val(),
                        type: 'GET',
                        dataType: 'json',
                        beforeSend: function() {
                            $("input[name='action']").val('');
                            $("input[name='action.inventory.name']").val('');
                            $("textarea[name='action.inventory.description']").val('');
                            $("input[name='action.warehouse.name']").val('');
                        },
                        success: function(action) {
                            console.log(action);
                            $("input[name='action']").val(action.id);
                            $("input[name='action.inventory.name']").val(action.inventory.name);
                            $("textarea[name='action.inventory.description']").val(action.inventory.description);
                            $("input[name='action.warehouse.name']").val(action.warehouse.name);
                            swal.close();
                        },
                        error: function() {
                            swal.fire({
                                title: "Xəta baş verdi!",
                                html: "Əlaqə saxlamağınızı xahiş edirik.",
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

    $('#modal-operation').on('shown.bs.modal', function() {
        $(document).off('focusin.modal');
    });

    var KTDatatablesBasicBasic = function() {

        var initTable1 = function() {
            var table = $('#schedule-table');

            table.DataTable({
                responsive: true,

                // DOM Layout settings
                dom: "<'row'<'col-sm-12'tr>><'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7 dataTables_pager'lp>>",

                lengthMenu: [5, 10, 25, 50, 75, 100, 200],

                pageLength: 25,

                language: {
                    'lengthMenu': 'Display _MENU_',
                },
                order: [[1, 'desc']],
                columnDefs: [
                    {
                        targets: 0,
                        width: '25px',
                        className: 'dt-center',
                        orderable: false
                    },
                ],
            });

            table.on('change', '.kt-group-checkable', function() {
                var set = $(this).closest('table').find('td:first-child .kt-checkable');
                var checked = $(this).is(':checked');

                $(set).each(function() {
                    if (checked) {
                        $(this).prop('checked', true);
                        $(this).closest('tr').addClass('active');
                    }
                    else {
                        $(this).prop('checked', false);
                        $(this).closest('tr').removeClass('active');
                    }
                });
            });

            table.on('change', 'tbody tr .kt-checkbox', function() {
                $(this).parents('tr').toggleClass('active');
            });
        };

        return {
            init: function() {
                initTable1();
            },
        };
    }();

    <c:if test="${edit.status}">
    $('#group_table tbody').on('dblclick', 'tr', function () {
        edit($('#form'), $(this).attr('data'), 'modal-operation', 'Redaktə');
    });
    </c:if>

    function exportContract(form, data){
        try {
            $(form).find("input[name='data']").val(data);
            submit(form);
        } catch (e) {
            console.error(e);
        }
    }
</script>