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
    <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
    <c:set var="filter" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'filter')}"/>
    <c:if test="${filter.status}">
        <div class="accordion  accordion-toggle-arrow mb-2" id="accordionFilter">
            <div class="card" style="border-radius: 4px;">
                <div class="card-header">
                    <div class="card-title w-100" data-toggle="collapse" data-target="#filterContent" aria-expanded="true" aria-controls="collapseOne4">
                        <div class="row w-100">
                            <div class="col-3">
                                <i class="<c:out value="${filter.object.icon}"/>"></i>
                                <c:out value="${list.totalElements>0?list.totalElements:0} sətr"/>
                            </div>
                            <div class="col-6 text-center" style="letter-spacing: 10px;">
                                <c:out value="${filter.object.name}"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="filterContent" class="collapse show" aria-labelledby="headingOne" data-parent="#accordionFilter">
                    <div class="card-body">
                        <form:form modelAttribute="filter" id="filter" method="post" action="/sale/sales/filter">
                            <form:hidden path="organization" />
                            <form:hidden path="service" />
                            <div class="row">
                                <div class="col-md-11">
                                    <div class="row">
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="id">KOD</form:label>
                                                <form:input path="id" cssClass="form-control" placeholder="######"/>
                                                <form:errors path="id" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="customer">Müştəri</form:label>
                                                <form:input path="customer" cssClass="form-control" placeholder="Müştəri kodu" />
                                                <form:errors path="customer" cssClass="alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="saleDateFrom">Satış tarixindən</form:label>
                                                <div class="input-group date">
                                                    <form:input path="saleDateFrom" autocomplete="off"
                                                                cssClass="form-control datepicker-element" date_="date_"
                                                                placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="saleDateFrom" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="saleDate">Tarixədək</form:label>
                                                <div class="input-group date">
                                                    <form:input path="saleDate" autocomplete="off"
                                                                cssClass="form-control datepicker-element" date_="date_"
                                                                placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="saleDate" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="guaranteeExpireFrom">Qarantiya bitir - tarixdən</form:label>
                                                <div class="input-group date">
                                                    <form:input path="guaranteeExpireFrom" autocomplete="off"
                                                                cssClass="form-control datepicker-element" date_="date_"
                                                                placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="guaranteeExpireFrom" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="guaranteeExpire">Tarixədək</form:label>
                                                <div class="input-group date">
                                                    <form:input path="guaranteeExpire" autocomplete="off"
                                                                cssClass="form-control datepicker-element" date_="date_"
                                                                placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="guaranteeExpire" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="payment.lastPriceFrom">Qiymətdən</form:label>
                                                <form:input path="payment.lastPriceFrom" cssClass="form-control" placeholder="Qiyməti daxil edin"/>
                                                <form:errors path="payment.lastPriceFrom" cssClass="alert-danger control-label"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="payment.lastPrice">Qiymətədək</form:label>
                                                <form:input path="payment.lastPrice" cssClass="form-control" placeholder="Qiyməti daxil edin"/>
                                                <form:errors path="payment.lastPrice" cssClass="alert-danger control-label"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="payment.period">Ödəniş edilsin</form:label>
                                                <form:select  path="payment.period.id" cssClass="custom-select form-control">
                                                    <form:option value=""></form:option>
                                                    <form:options items="${payment_periods}" itemLabel="name" itemValue="id" />
                                                </form:select>
                                                <form:errors path="payment.period" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="payment.schedule">Ödəniş qrafiki</form:label>
                                                <form:select  path="payment.schedule.id" cssClass="custom-select form-control">
                                                    <form:option value=""></form:option>
                                                    <form:options items="${payment_schedules}" itemLabel="name" itemValue="id" />
                                                </form:select>
                                                <form:errors path="payment.schedule" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="row" style="padding-top: 30px;">
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label class="kt-checkbox kt-checkbox--brand">
                                                        <form:checkbox path="payment.cash"/> Nağdlar
                                                        <span></span>
                                                    </label>
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label class="kt-checkbox kt-checkbox--brand">
                                                        <form:checkbox path="approve"/> Təsdiqlər
                                                        <span></span>
                                                    </label>
                                                </div>
                                            </div>
                                            <c:if test="${delete.status}">
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label class="kt-checkbox kt-checkbox--brand">
                                                            <form:checkbox path="active"/> Aktual
                                                            <span></span>
                                                        </label>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <form:label path="canavasser">Canavasser</form:label>
                                                <form:select  path="canavasser.id" cssClass="custom-select form-control" multiple="single">
                                                    <form:option value=""></form:option>
                                                    <c:forEach var="itemGroup" items="${employees}" varStatus="itemGroupIndex">
                                                        <optgroup label="${itemGroup.key}">
                                                            <form:options items="${itemGroup.value}" itemLabel="person.fullName" itemValue="id"/>
                                                        </optgroup>
                                                    </c:forEach>
                                                </form:select>
                                                <form:errors path="canavasser" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <form:label path="dealer">Diller</form:label>
                                                <form:select  path="dealer.id" cssClass="custom-select form-control" multiple="single">
                                                    <form:option value=""></form:option>
                                                    <c:forEach var="itemGroup" items="${employees}" varStatus="itemGroupIndex">
                                                        <optgroup label="${itemGroup.key}">
                                                            <form:options items="${itemGroup.value}" itemLabel="person.fullName" itemValue="id"/>
                                                        </optgroup>
                                                    </c:forEach>
                                                </form:select>
                                                <form:errors path="dealer" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <form:label path="console">Konsul</form:label>
                                                <form:select  path="console.id" cssClass="custom-select form-control">
                                                    <form:option value=""></form:option>
                                                    <c:forEach var="itemGroup" items="${employees}" varStatus="itemGroupIndex">
                                                        <optgroup label="${itemGroup.key}">
                                                            <form:options items="${itemGroup.value}" itemLabel="person.fullName" itemValue="id"/>
                                                        </optgroup>
                                                    </c:forEach>
                                                </form:select>
                                                <form:errors path="console" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <form:label path="vanLeader">Van lider</form:label>
                                                <form:select  path="vanLeader.id" cssClass="custom-select form-control" multiple="single">
                                                    <form:option value=""></form:option>
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
                                <div class="col-md-1 text-right">
                                    <div class="form-group">
                                        <a href="#" onclick="location.reload();" class="btn btn-danger btn-elevate btn-icon-sm btn-block mb-2" style="padding: 0.35rem 0.6rem;">
                                            <i class="la la-trash"></i> Sil
                                        </a>
                                        <a href="#" onclick="submit($('#filter'))" class="btn btn-warning btn-elevate btn-icon-sm btn-block mt-2" style="padding: 0.35rem 0.6rem">
                                            <i class="la la-search"></i> Axtar
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
    <div class="row">
        <div class="col-lg-12">
            <div class="kt-portlet kt-portlet--mobile">
                <div class="kt-portlet__body">
                    <c:choose>
                        <c:when test="${not empty list}">
                            <c:set var="view" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'view')}"/>
                            <c:set var="view1" value="${utl:checkOperation(sessionScope.user.userModuleOperations, 'contact-history', 'view')}"/>
                            <c:set var="view2" value="${utl:checkOperation(sessionScope.user.userModuleOperations, 'schedule', 'view')}"/>
                            <c:set var="view3" value="${utl:checkOperation(sessionScope.user.userModuleOperations, 'service-regulator', 'view')}"/>
                            <c:set var="view4" value="${utl:checkOperation(sessionScope.user.userModuleOperations, 'customer', 'view')}"/>
                            <c:set var="view5" value="${utl:checkOperation(sessionScope.user.userModuleOperations, 'invoice', 'view')}"/>
                            <c:set var="view6" value="${utl:checkOperation(sessionScope.user.userModuleOperations, 'inventory', 'view')}"/>
                            <c:set var="approve" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'approve')}"/>
                            <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
                            <c:set var="export" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'export')}"/>
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="datatable">
                                <thead>
                                <tr>
                                    <th>Kod</th>
                                    <th>İnventar</th>
                                    <th>Satış tarixi</th>
                                    <th>Müştəri</th>
                                    <th>Qiymət</th>
                                    <th>Ödənilib</th>
                                    <th>Qrafik</th>
                                    <th>Satış komandası</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list.content}" varStatus="loop">
                                    <tr data="<c:out value="${t.id}" />" style="<c:out value="${t.saled?'background-color: rgb(237, 239, 255) !important':''}"/>">
                                        <td style="<c:out value="${t.payment.cash?'background-color: #e6ffe7 !important':'background-color: #ffeaf1 !important'}"/>"><c:out value="${t.id}" /></td>
                                        <th>
                                            <c:forEach var="p" items="${t.salesInventories}" varStatus="lp">
                                                <c:out value="${lp.index+1}" />.
                                                <c:out value="${p.inventory.name}" /><br/>
                                                <c:out value="${p.inventory.barcode}" />
                                            </c:forEach>
                                        </th>
                                        <td><fmt:formatDate value = "${t.saleDate}" pattern = "dd.MM.yyyy" /></td>
                                        <th>
                                            <c:choose>
                                                <c:when test="${view4.status}">
                                                    <a href="javascript:window.open('/crm/customer/<c:out value="${t.customer.id}"/>', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder"><c:out value="${t.customer.id}"/>: <c:out value="${t.customer.person.fullName}"/></a>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:out value="${t.payment.sales.customer.id}"/>: <c:out value="${t.customer.person.fullName}"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </th>
                                        <th>
                                            Qiymət: <c:out value="${t.payment.price}" /><br/>
                                            <c:if test="${not empty t.payment.discount}">
                                                Endirim: <c:out value="${t.payment.discount}" /><br/>
                                            </c:if>
                                            <c:if test="${not empty t.payment.description}">
                                                Səbəbi: <c:out value="${t.payment.description}" /><br/>
                                            </c:if>
                                            <c:if test="${t.payment.down>0}">
                                                İlkin ödəniş: <c:out value="${t.payment.down}" /><br/>
                                            </c:if>
                                            <c:if test="${!t.payment.cash}">
                                                Qrafik üzrə: <c:out value="${t.payment.schedulePrice}" /><br/>
                                            </c:if>
                                            Son qiymət: <c:out value="${t.payment.lastPrice}" />
                                        </th>
                                        <th>
                                            <c:set var="payable" value="${utl:calculateInvoice(t.invoices)}"/>
                                            <c:choose>
                                                <c:when test="${view5.status}">
                                                    <a href="javascript:window.open('/sale/invoice/<c:out value="${t.id}"/>', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder"><c:out value="${payable}"/> AZN</a>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:out value="${payable}"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </th>
                                        <td>
                                            <c:if test="${!t.payment.cash}">
                                                Qrafik: <c:out value="${t.payment.schedule.name}" /><br/>
                                            </c:if>
                                            <c:if test="${!t.payment.cash}">
                                                Period: <c:out value="${t.payment.period.name}" /><br/>
                                            </c:if>
                                            Zəmanət müddəti: <c:out value="${t.guarantee}" /> ay<br/>
                                            Zəmanət bitir: <fmt:formatDate value = "${t.guaranteeExpire}" pattern = "dd.MM.yyyy" />
                                        </td>
                                        <td>
                                            Konsul: <c:out value="${t.console.person.fullName}" /><br/>
                                            Ven lider: <c:out value="${t.vanLeader.person.fullName}" /><br/>
                                            Diller: <c:out value="${t.dealer.person.fullName}" /><br/>
                                            Canvasser: <c:out value="${t.canavasser.person.fullName}" />
                                        </td>
                                        <td nowrap class="text-center">
                                            <c:if test="${approve.status and !t.approve}">
                                                <a href="javascript:approveData($('#approve-form'),'<c:out value="${t.id}" /> nömrəli müqavilənin təsdiqi', '<c:out value="${t.id}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${approve.object.name}"/>">
                                                    <i class="<c:out value="${approve.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${view.status}">
                                                <a href="javascript:view($('#form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${view.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${view.object.name}"/>">
                                                    <i class="<c:out value="${view.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                            <span class="dropdown">
                                                <a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" data-toggle="dropdown" aria-expanded="true">
                                                  <i class="la la-ellipsis-h"></i>
                                                </a>
                                                <div class="dropdown-menu dropdown-menu-right">
                                                    <c:if test="${view1.status}">
                                                    <a href="/collect/contact-history/<c:out value="${t.id}"/>" class="dropdown-item">
                                                        <i class="la <c:out value="${view1.object.icon}"/>"></i> Qeydlər
                                                    </a>
                                                    </c:if>
                                                    <c:if test="${view2.status}">
                                                    <a href="/sale/schedule/<c:out value="${t.id}"/>" class="dropdown-item">
                                                        <i class="la <c:out value="${view2.object.icon}"/>"></i> Ödəniş qrafiki
                                                    </a>
                                                    </c:if>
                                                    <c:if test="${view3.status}">
                                                    <a href="/sale/service-regulator/<c:out value="${t.id}"/>" class="dropdown-item">
                                                        <i class="la <c:out value="${view3.object.icon}"/>"></i> Servis requlyatoru
                                                    </a>
                                                    </c:if>
                                                    <c:if test="${edit.status and !t.approve}">
                                                    <a href="javascript:edit($('#form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${edit.object.name}" />');" class="dropdown-item" title="<c:out value="${edit.object.name}"/>">
                                                        <i class="<c:out value="${edit.object.icon}"/>"></i> <c:out value="${edit.object.name}"/>
                                                    </a>
                                                    </c:if>
                                                    <c:if test="${delete.status and !t.approve}">
                                                    <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.salesInventories.get(0).inventory.name}" /> <br/> <c:out value="${t.customer.person.fullName}" />');" class="dropdown-item" title="<c:out value="${delete.object.name}"/>">
                                                        <i class="<c:out value="${delete.object.icon}"/>"></i> <c:out value="${delete.object.name}"/>
                                                    </a>
                                                    </c:if>
                                                </div>
                                            </span>
                                            <c:if test="${export.status}">
                                                <a href="javascript:exportContract($('#form-export-contract'), '<c:out value="${t.id}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="Müqavilənin ixracı">
                                                    <i class="<c:out value="${export.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
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
                                        <i class="flaticon-price-tag"></i>
                                    </div>
                                    <div class="kt-wizard-v1__nav-label">
                                        1. Satış
                                    </div>
                                </div>
                            </div>
                            <div class="kt-wizard-v1__nav-item" data-ktwizard-type="step">
                                <div class="kt-wizard-v1__nav-body">
                                    <div class="kt-wizard-v1__nav-icon">
                                        <i class="flaticon-list"></i>
                                    </div>
                                    <div class="kt-wizard-v1__nav-label">
                                        2. Müştəri
                                    </div>
                                </div>
                            </div>
                            <div class="kt-wizard-v1__nav-item" data-ktwizard-type="step">
                                <div class="kt-wizard-v1__nav-body">
                                    <div class="kt-wizard-v1__nav-icon">
                                        <i class="flaticon-bus-stop"></i>
                                    </div>
                                    <div class="kt-wizard-v1__nav-label">
                                        3. Əlaqə
                                    </div>
                                </div>
                            </div>
                            <div class="kt-wizard-v1__nav-item" data-ktwizard-type="step">
                                <div class="kt-wizard-v1__nav-body">
                                    <div class="kt-wizard-v1__nav-icon">
                                        <i class="flaticon-truck"></i>
                                    </div>
                                    <div class="kt-wizard-v1__nav-label">
                                        4. Satıcı
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!--end: Form Wizard Nav -->
                </div>
                <div class="kt-grid__item kt-grid__item--fluid kt-wizard-v1__wrapper">
                <form:form modelAttribute="form" id="form" method="post" action="/sale/sales" cssClass="form-group kt-form">
                    <form:hidden path="id"/>
                    <form:hidden path="active"/>
                    <form:hidden path="service"/>
                    <form:hidden path="organization"/>
                    <input type="hidden" name="customer.organization" value="<c:out value="${sessionScope.organization.id}"/>"/>
                    <div class="kt-wizard-v1__content" data-ktwizard-type="step-content" data-ktwizard-state="current">
                        <div class="kt-form__section kt-form__section--first">
                            <div class="kt-wizard-v1__form">
                                <div class="row">
                                    <div class="col-md-5">
                                        <input type="hidden" name="salesInventories[0].inventory" class="form-control"/>
                                        <form:label path="saleDate">Satış tarixi</form:label>
                                        <div class="row">
                                            <div class="col-8">
                                                <div class="form-group">
                                                    <div class="input-group date" >
                                                        <div class="input-group-prepend"><span class="input-group-text"><i class="la la-calendar"></i></span></div>
                                                        <form:input path="saleDate" cssClass="form-control datepicker-element" date_="date_" placeholder="dd.MM.yyyy"/>
                                                    </div>
                                                    <form:errors path="saleDate" cssClass="control-label alert-danger" />
                                                </div>
                                            </div>
                                            <c:if test="${view6.status}">
                                                <div class="col-4">
                                                    <a href="javascript:window.open('/warehouse/inventory', 'mywindow', 'width=1250, height=800')" data-repeater-delete="" class="btn btn-label-success btn-block">
                                                        İnventar
                                                    </a>
                                                </div>
                                            </c:if>

                                        </div>
                                        <div class="form-group">
                                            <label>Barkod üzrə inventar axtarışı</label>
                                            <div class="row">
                                                <div class="col-8">
                                                    <div class="form-group">
                                                        <div class="input-group">
                                                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-search"></i></span></div>
                                                            <form:input path="salesInventories[0].inventory.barcode" class="form-control" placeholder="Barkodu daxil edin..." />
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <a href="javascript:findInventory($('#form'), $('#form').find('input[name=\'salesInventories[0].inventory.barcode\']'));" data-repeater-delete="" class="btn btn-label-brand btn-block">
                                                        Axtar
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-7">
                                                <div class="form-group">
                                                    <label>İnventar</label>
                                                    <form:input path="salesInventories[0].inventory.name" class="form-control" readonly="true"/>
                                                </div>
                                                <div class="form-group">
                                                    <label>Qrup</label>
                                                    <form:input path="salesInventories[0].inventory.group.name" class="form-control" readonly="true"/>
                                                </div>
                                            </div>
                                            <div class="col-md-5">
                                                <div class="form-group">
                                                    <label>Açıqlama</label>
                                                    <form:textarea rows="4" path="salesInventories[0].inventory.description" class="form-control" readonly="true" />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-7">
                                        <div class="row">
                                            <div class="col-sm-4">
                                                <form:label path="payment.price">Qiymət</form:label>
                                                <form:select  path="payment.price" onchange="calculate($(this))" cssClass="custom-select form-control">
                                                    <form:options items="${sale_prices}" itemLabel="name" itemValue="attr1" />
                                                </form:select>
                                                <form:errors path="payment.price" cssClass="control-label alert-danger"/>
                                            </div>
                                            <div class="col-sm-4 text-center">
                                                <div class="form-group mt-3 pt-4">
                                                    <label class="kt-checkbox kt-checkbox--brand">
                                                        <form:checkbox path="payment.cash" onclick="doCash($(this), '10%')"/> Nağd ödəniş?
                                                        <span></span>
                                                    </label>
                                                </div>
                                            </div>
                                            <div class="col-sm-4">
                                                <form:label path="guarantee">Zəmanət müddəti</form:label>
                                                <form:select  path="guarantee" cssClass="custom-select form-control">
                                                    <form:options items="${guarantees}" itemLabel="name" itemValue="attr1" />
                                                </form:select>
                                                <form:errors path="guarantee" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="row kt-hidden animated zoomIn" id="cash-div">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <form:label path="payment.discount">Endirim dəyəri</form:label>
                                                    <form:input path="payment.discount" cssClass="form-control" readonly="true" cssStyle="text-align: -webkit-center; text-align: center; font-weight: bold; letter-spacing: 3px;"/>
                                                    <form:errors path="payment.discount" cssClass="control-label alert-danger"/>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <form:label path="payment.description">Açıqlama</form:label>
                                                    <form:input path="payment.description" cssClass="form-control" readonly="true"/>
                                                    <form:errors path="payment.description" cssClass="control-label alert-danger"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="alert alert-info alert-elevate" role="alert" style="padding: 0; padding-left: 1rem; padding-right: 1rem; padding-top: 5px;">
                                                    <div class="alert-icon"><i class="flaticon-warning kt-font-brand kt-font-light"></i></div>
                                                    <div class="alert-text text-center">
                                                        <div style="font-size: 15px; font-weight: bold; letter-spacing: 1px;">
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
                                            <div class="col-sm-9">
                                                <div class="row">
                                                    <div class="col-sm-6">
                                                        <div class="form-group">
                                                            <form:label path="payment.schedule">Ödəniş qrafiki</form:label>
                                                            <form:select  path="payment.schedule" cssClass="custom-select form-control">
                                                                <form:options items="${payment_schedules}" itemLabel="name" itemValue="id" />
                                                            </form:select>
                                                            <form:errors path="payment.schedule" cssClass="control-label alert-danger"/>
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-6">
                                                        <div class="form-group">
                                                            <form:label path="payment.period">Ödəniş edilsin</form:label>
                                                            <form:select  path="payment.period" cssClass="custom-select form-control">
                                                                <form:options items="${payment_periods}" itemLabel="name" itemValue="id" />
                                                            </form:select>
                                                            <form:errors path="payment.period" cssClass="control-label alert-danger"/>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-sm-6">
                                                        <div class="form-group">
                                                            <form:label path="payment.down">İlkin ödəniş</form:label>
                                                            <div class="input-group" >
                                                                <div class="input-group-prepend"><span class="input-group-text"><i class="la la-usd"></i></span></div>
                                                                <form:input path="payment.down" cssClass="form-control" placeholder="İlkin ödənişi daxil edin"/>
                                                            </div>
                                                            <form:errors path="payment.down" cssClass="alert-danger control-label"/>
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-6">
                                                        <div class="form-group">
                                                            <form:label path="payment.schedulePrice">Qrafik məbləği</form:label>
                                                            <div class="input-group" >
                                                                <div class="input-group-prepend"><span class="input-group-text"><i class="la la-usd"></i></span></div>
                                                                <form:input path="payment.schedulePrice" cssClass="form-control" readonly="true"/>
                                                            </div>
                                                            <form:errors path="payment.schedulePrice" cssClass="control-label alert-danger"/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-sm-3 text-center">
                                                <label>&nbsp;</label>
                                                <button type="button" class="btn btn-outline-info btn-tallest" style="font-size: 15px;font-weight: bolder; padding-left: 7px; padding-right: 8px;" onclick="schedule($('#form'))"><i class="fa fa-play"></i> Qrafik məbləğini hesabla</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12" id="schedule-div">

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                        <div class="kt-wizard-v1__content" data-ktwizard-type="step-content">
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
                                                        <button class="btn btn-primary" type="button" onclick="findCustomer($('#form'), $('#form').find('input[name=\'customer\']'))">Müştərini axtar</button>
                                                    </div>
                                                </div>
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
                                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-calendar"></i></span></div>
                                                    <form:input path="customer.person.birthday" cssClass="form-control datepicker-element" date_="date_" placeholder="dd.MM.yyyy"/>
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
                                                <div class="input-group">
                                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-phone"></i></span></div>
                                                    <form:input path="customer.person.contact.mobilePhone" cssClass="form-control" placeholder="0505505550"/>
                                                </div>
                                                <form:errors path="customer.person.contact.mobilePhone" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.homePhone">Şəhər nömrəsi</form:label>
                                                <div class="input-group" >
                                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-phone"></i></span></div>
                                                    <form:input path="customer.person.contact.homePhone" cssClass="form-control" placeholder="0124555050"/>
                                                </div>
                                                <form:errors path="customer.person.contact.homePhone" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.email">Email</form:label>
                                                <div class="input-group" >
                                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-at"></i></span></div>
                                                    <form:input path="customer.person.contact.email" cssClass="form-control" placeholder="example@example.com"/>
                                                </div>
                                                <form:errors path="customer.person.contact.email" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.relationalPhoneNumber1">Əlaqəli şəxs nömrəsi #1</form:label>
                                                <div class="input-group">
                                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-phone"></i></span></div>
                                                    <form:input path="customer.person.contact.relationalPhoneNumber1" cssClass="form-control" placeholder="0505505550"/>
                                                </div>
                                                <form:errors path="customer.person.contact.relationalPhoneNumber1" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.relationalPhoneNumber2">Əlaqəli şəxs nömrəsi #2</form:label>
                                                <div class="input-group">
                                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-phone"></i></span></div>
                                                    <form:input path="customer.person.contact.relationalPhoneNumber2" cssClass="form-control" placeholder="0505505550"/>
                                                </div>
                                                <form:errors path="customer.person.contact.relationalPhoneNumber2" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.relationalPhoneNumber3">Əlaqəli şəxs nömrəsi #3</form:label>
                                                <div class="input-group" >
                                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-phone"></i></span></div>
                                                    <form:input path="customer.person.contact.relationalPhoneNumber3" cssClass="form-control" placeholder="0505505550"/>
                                                </div>
                                                <form:errors path="customer.person.contact.relationalPhoneNumber3" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.geolocation">Geolocation</form:label>
                                                <div class="input-group" >
                                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-map-marker"></i></span></div>
                                                    <form:input path="customer.person.contact.geolocation" cssClass="form-control" readonly="true"/>
                                                </div>
                                                <form:errors path="customer.person.contact.geolocation" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.city">Şəhər</form:label>
                                                <form:select  path="customer.person.contact.city" cssClass="custom-select form-control">
                                                    <form:option value=""></form:option>
                                                    <form:options items="${cities}" itemLabel="name" itemValue="id" />
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-9">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.address">Ünvan</form:label>
                                                <div class="input-group" >
                                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-street-view"></i></span></div>
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
                                                    <form:option value=""></form:option>
                                                    <form:options items="${cities}" itemLabel="name" itemValue="id" />
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-9">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.livingAddress">Yaşayış ünvanı</form:label>
                                                <div class="input-group" >
                                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-street-view"></i></span></div>
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
                                <div class="kt-wizard-v1__review">
                                    <div class="row">
                                        <div class="col-md-5 offset-md-1">
                                            <div class="form-group">
                                                <form:label path="console">Konsul</form:label>
                                                <form:select  path="console" cssClass="custom-select form-control">
                                                    <form:option value=""></form:option>
                                                    <c:forEach var="itemGroup" items="${employees}" varStatus="itemGroupIndex">
                                                        <optgroup label="${itemGroup.key}">
                                                            <form:options items="${itemGroup.value}" itemLabel="person.fullName" itemValue="id"/>
                                                        </optgroup>
                                                    </c:forEach>
                                                </form:select>
                                                <form:errors path="console" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-5">
                                            <div class="form-group">
                                                <form:label path="vanLeader">Van lider</form:label>
                                                <form:select  path="vanLeader" cssClass="custom-select form-control" multiple="single">
                                                    <form:option value=""></form:option>
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
                                    <div class="kt-separator kt-separator--border-dashed kt-separator--space-sm kt-separator--portlet-fit" style="margin: 1rem 0"></div>
                                    <div class="row">
                                        <div class="col-md-5 offset-md-1">
                                            <div class="form-group">
                                                <form:label path="dealer">Diller</form:label>
                                                <form:select  path="dealer" cssClass="custom-select form-control" multiple="single">
                                                    <form:option value=""></form:option>
                                                    <c:forEach var="itemGroup" items="${employees}" varStatus="itemGroupIndex">
                                                        <optgroup label="${itemGroup.key}">
                                                            <form:options items="${itemGroup.value}" itemLabel="person.fullName" itemValue="id"/>
                                                        </optgroup>
                                                    </c:forEach>
                                                </form:select>
                                                <form:errors path="dealer" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-5">
                                            <div class="form-group">
                                                <form:label path="canavasser">Canavasser</form:label>
                                                <form:select  path="canavasser" cssClass="custom-select form-control" multiple="single">
                                                    <form:option value=""></form:option>
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
                        <div class="kt-form__actions">
                            <button class="btn btn-secondary btn-md btn-tall btn-wide kt-font-bold kt-font-transform-u" data-ktwizard-type="action-prev">
                                Geri
                            </button>
                            <button class="btn btn-success btn-md btn-tall btn-wide kt-font-bold kt-font-transform-u modal-footer" data-ktwizard-type="action-submit">
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

<form id="approve-form" method="post" action="/sale/sales/approve" style="display: none">
    <input type="hidden" name="id" />
</form>

<script>

    $("#datatable").DataTable({
        responsive: true,
        lengthMenu: [10, 25, 50, 75, 100, 200, 1000],
        pageLength: 100,
        order: [[1, 'desc']],
        columnDefs: [
            {
                targets: 0,
                width: '50px',
                className: 'dt-center',
                orderable: false
            },
        ],
    });

    function schedule(form){
        var table='';
        swal.fire({
            text: 'Proses davam edir...',
            allowOutsideClick: false,
            onOpen: function() {
                swal.showLoading();
                $.ajax({
                    url: '/sale/payment/schedule/' + $(form).find("input[name='payment.lastPrice']").val() + '/' + $(form).find("input[name='payment.down']").val() + '/' + $(form).find("select[name='payment.schedule']").val() + '/' + $(form).find("select[name='payment.period']").val() + '/' + $(form).find("input[name='saleDate']").val(),
                    type: 'GET',
                    dataType: 'json',
                    beforeSend: function() {
                        table+="<table class='table table-striped- table-bordered table-hover table-checkable  animated zoomIn' id='schedule-table'><thead><th style='width: 20px;' class='text-center'>№</th><th>Ödəniş tarixi</th><th>Ödəniş məbləği</th></thead>"
                    },
                    success: function(data) {
                        console.log(data);
                        table+="<tbody>";
                        $.each(data, function(k, v) {
                            table+="<tr>" +
                                "<th>"+(parseInt(k)+1)+"</th>" +
                                "<th>" +
                                v.scheduleDate.split(' ')[0]+
                                "</th>" +
                                "<th>" +
                                v.amount+
                                " <i style='font-style: italic; font-size: 10px;'>AZN<i></th></tr>";
                            $(form).find("input[name='payment.schedulePrice']").val(v.amount);
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
                        $("#schedule-table").DataTable({pageLength: 10});
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
                    customer: {
                        required: false,
                        digits: true
                    },
                    'customer.person.firstName': {
                        required: true
                    },
                    'customer.person.lastName': {
                        required: true
                    },
                    'customer.person.contact.mobilePhone': {
                        required: true
                    },
                    'saleDate': {
                        required: true,
                    },
                    'customer.person.contact.city': {
                        required: true
                    },
                    'customer.person.contact.address': {
                        required: true
                    },
                    'salesInventories[0].inventory.name': {
                        required: true
                    },
                    'salesInventories[0].inventory.barcode': {
                        required: true
                    },
                    'payment.down': {
                        required: true,
                        number: true,
                        min: 0
                    },
                    'payment.schedulePrice': {
                        required: false,
                        number: true,
                        min: 0
                    },
                    console: {
                        required: true
                    },
                    vanLeader: {
                        required: true
                    },
                    dealer: {
                        required: true
                    },
                    canavasser: {
                        required: true
                    },
                },
                invalidHandler: function(event, validator) {
                    KTUtil.scrollTop();
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
                            toastr.success("Əməliyyat uğurla yerinə yetirildi!");
                            location.reload();
                        }
                    });
                }
            });
        };
        return {
            init: function() {
                wizardEl = KTUtil.get('kt_wizard_v1');
                formEl = $('#form');
                initWizard();
                initValidation();
                initSubmit();
            }
        };
    }();

    jQuery(document).ready(function() {
        KTWizard1.init();
    });

    function findCustomer(form, element){
        if($(element).val().trim().length>0){
            swal.fire({
                text: 'Proses davam edir...',
                allowOutsideClick: false,
                onOpen: function() {
                    swal.showLoading();
                    $.ajax({
                        url: '/crm/api/customer/'+$(element).val(),
                        type: 'GET',
                        dataType: 'json',
                        beforeSend: function() {
                            $(form).find("input[name='customer.person.firstName']").val('');
                            $(form).find("input[name='customer.person.lastName']").val('');
                            $(form).find("input[name='customer.person.fatherName']").val('');
                            $(form).find("input[name='customer.person.birthday']").val('');
                            $(form).find("input[name='customer.person.idCardSerialNumber']").val('');
                            $(form).find("input[name='customer.person.idCardPinCode']").val('');
                            $(form).find("input[name='customer.person.contact.mobilePhone']").val('');
                            $(form).find("input[name='customer.person.contact.homePhone']").val('');
                            $(form).find("input[name='customer.person.contact.email']").val('');
                            $(form).find("input[name='customer.person.contact.relationalPhoneNumber1']").val('');
                            $(form).find("input[name='customer.person.contact.relationalPhoneNumber2']").val('');
                            $(form).find("input[name='customer.person.contact.relationalPhoneNumber3']").val('');
                        },
                        success: function(customer) {
                            console.log(customer);
                            $(form).find("input[name='customer.person.firstName']").val(customer.person.firstName);
                            $(form).find("input[name='customer.person.lastName']").val(customer.person.lastName);
                            $(form).find("input[name='customer.person.fatherName']").val(customer.person.fatherName);
                            $(form).find("input[name='customer.person.birthday']").val(getFormattedDate(new Date(customer.person.birthday)));
                            $(form).find("input[name='customer.person.idCardSerialNumber']").val(customer.person.idCardSerialNumber);
                            $(form).find("input[name='customer.person.idCardPinCode']").val(customer.person.idCardPinCode);
                            $(form).find("input[name='customer.person.contact.mobilePhone']").val(customer.person.contact.mobilePhone);
                            $(form).find("input[name='customer.person.contact.homePhone']").val(customer.person.contact.homePhone);
                            $(form).find("input[name='customer.person.contact.email']").val(customer.person.contact.email);
                            $(form).find("input[name='customer.person.contact.relationalPhoneNumber1']").val(customer.person.contact.relationalPhoneNumber1);
                            $(form).find("input[name='customer.person.contact.relationalPhoneNumber2']").val(customer.person.contact.relationalPhoneNumber2);
                            $(form).find("input[name='customer.person.contact.relationalPhoneNumber3']").val(customer.person.contact.relationalPhoneNumber3);
                            if(customer.person.contact.city!=null){
                                $(form).find("select[name='customer.person.contact.city'] option[value="+customer.person.contact.city.id+"]").attr("selected", "selected");
                            }
                            $(form).find("input[name='customer.person.contact.address']").val(customer.person.contact.address);
                            if(customer.person.contact.livingCity!=null){
                                $(form).find("select[name='customer.person.contact.livingCity'] option[value="+customer.person.contact.livingCity.id+"]").attr("selected", "selected");
                            }
                            $(form).find("input[name='customer.person.contact.livingAddress']").val(customer.person.contact.livingAddress);
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

    function findInventory(form, element){
        if($(element).val().trim().length>0){
            swal.fire({
                text: 'Proses davam edir...',
                allowOutsideClick: false,
                onOpen: function() {
                    swal.showLoading();
                    $.ajax({
                        url: '/warehouse/api/inventory/'+$(element).val(),
                        type: 'GET',
                        dataType: 'json',
                        beforeSend: function() {
                            $(form).find("input[name='salesInventories[0].inventory']").val('');
                            $(form).find("input[name='salesInventories[0].inventory.name']").val('');
                            $(form).find("input[name='salesInventories[0].inventory.group.name']").val('');
                            $(form).find("textarea[name='salesInventories[0].inventory.description']").val('');
                        },
                        success: function(inventory) {
                            console.log(inventory);
                            $(form).find("input[name='salesInventories[0].inventory']").val(inventory.id);
                            $(form).find("input[name='salesInventories[0].inventory.name']").val(inventory.name);
                            $(form).find("input[name='salesInventories[0].inventory.group.name']").val(inventory.group.name);
                            $(form).find("textarea[name='salesInventories[0].inventory.description']").val(inventory.description);
                            swal.close();
                        },
                        error: function() {
                            swal.fire({
                                title: "Xəta baş verdi!",
                                html: "<c:out value="${sessionScope.user.employee.person.lastName}"/> <c:out value="${sessionScope.user.employee.person.firstName}"/> adına inventar təhkim edilməyib",
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

    <c:if test="${view2.status}">
    $('#datatable tbody').on('dblclick', 'tr', function () {
        swal.showLoading();
        location.href = '/sale/schedule/'+ $(this).attr('data');
        window.reload();
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

    $("input[name='customer.person.contact.email']").inputmask({
        mask: "*{1,20}[.*{1,20}][.*{1,20}][.*{1,20}]@*{1,20}[.*{2,6}][.*{1,2}]",
        greedy: false,
        onBeforePaste: function (pastedValue, opts) {
            pastedValue = pastedValue.toLowerCase();
            return pastedValue.replace("mailto:", "");
        },
        definitions: {
            '*': {
                validator: "[0-9A-Za-z!#$%&'*+/=?^_`{|}~\-]",
                cardinality: 1,
                casing: "lower"
            }
        }
    });

    $("input[name='customer.person.contact.mobilePhone']").inputmask("mask", {
        "mask": "(999) 999-9999"
    });

    $("input[name='customer.person.contact.homePhone']").inputmask("mask", {
        "mask": "(999) 999-9999"
    });

    $("input[name='customer.person.contact.relationalPhoneNumber1']").inputmask("mask", {
        "mask": "(999) 999-9999"
    });

    $("input[name='customer.person.contact.relationalPhoneNumber2']").inputmask("mask", {
        "mask": "(999) 999-9999"
    });

    $("input[name='customer.person.contact.relationalPhoneNumber3']").inputmask("mask", {
        "mask": "(999) 999-9999"
    });

    $("input[name='payment.down']").inputmask('decimal', {
        rightAlignNumerics: false
    });

    $("input[name='payment.schedulePrice']").inputmask('decimal', {
        rightAlignNumerics: false
    });

    function showPosition(position) {
        var geolocation =  $("#form").find("input[name='customer.person.contact.geolocation']");
        if($(geolocation).val().trim().length==0){
            $(geolocation).val(position.coords.latitude + ',' + position.coords.longitude);
        }
    }

    $(function(){
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(showPosition);
        }
    });

    function approveData(form, info, id){
        swal.fire({
            title: 'Əminsinizmi?',
            html: info,
            type: 'success',
            allowEnterKey: true,
            showCancelButton: true,
            buttonsStyling: false,
            cancelButtonText: 'İmtina',
            cancelButtonColor: '#d1d5cf',
            cancelButtonClass: 'btn btn-default',
            confirmButtonText: 'Bəli, icra edilsin!',
            confirmButtonColor: '#c40000',
            confirmButtonClass: 'btn btn-success',
            footer: '<a href>Məlumatlar yenilənsinmi?</a>'
        }).then(function(result) {
            if (result.value) {
                swal.fire({
                    text: 'Proses davam edir...',
                    allowOutsideClick: false,
                    onOpen: function() {
                        $(form).find("input[name='id']").val(id);
                        swal.showLoading();
                        $(form).submit();
                    }
                })
            }
        })
    }
</script>