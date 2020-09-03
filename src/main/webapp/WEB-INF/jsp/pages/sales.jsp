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
                            <form:hidden path="organization.id" />
                            <form:hidden path="service" />
                            <div class="row">
                                <div class="col-md-11">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="row">
                                                <div class="col-md-3">
                                                    <div class="form-group">
                                                        <form:label path="id">KOD</form:label>
                                                        <form:input path="id" cssClass="form-control" placeholder="######"/>
                                                        <form:errors path="id" cssClass="control-label alert-danger"/>
                                                    </div>
                                                </div>
                                                <div class="col-md-3">
                                                    <div class="form-group">
                                                        <form:label path="customer.id">Müştəri</form:label>
                                                        <form:input path="customer.id" cssClass="form-control" placeholder="Müştəri kodu" />
                                                        <form:errors path="customer.id" cssClass="alert-danger"/>
                                                    </div>
                                                </div>
                                                <div class="col-md-3">
                                                    <div class="form-group">
                                                        <form:label path="payment.lastPriceFrom">Qiymətdən</form:label>
                                                        <form:input path="payment.lastPriceFrom" cssClass="form-control" placeholder="Daxil edin"/>
                                                        <form:errors path="payment.lastPriceFrom" cssClass="alert-danger control-label"/>
                                                    </div>
                                                </div>
                                                <div class="col-md-3">
                                                    <div class="form-group">
                                                        <form:label path="payment.lastPrice">Qiymətədək</form:label>
                                                        <form:input path="payment.lastPrice" cssClass="form-control" placeholder="Daxil edin"/>
                                                        <form:errors path="payment.lastPrice" cssClass="alert-danger control-label"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="row">
                                                <div class="col-md-3">
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
                                                <div class="col-md-3">
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
                                                <div class="col-md-3">
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
                                                <div class="col-md-3">
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
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="taxConfiguration.id">VÖEN</form:label>
                                                <form:select  path="taxConfiguration.id" cssClass="custom-select form-control">
                                                    <form:option value=""></form:option>
                                                    <form:options items="${tax_configurations}" itemLabel="label" itemValue="id" />
                                                </form:select>
                                                <form:errors path="taxConfiguration.id" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="payment.period.id">Ödəniş edilsin</form:label>
                                                <form:select  path="payment.period.id" cssClass="custom-select form-control">
                                                    <form:option value=""></form:option>
                                                    <form:options items="${payment_periods}" itemLabel="name" itemValue="id" />
                                                </form:select>
                                                <form:errors path="payment.period.id" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="payment.schedule.id">Ödəniş qrafiki</form:label>
                                                <form:select  path="payment.schedule.id" cssClass="custom-select form-control">
                                                    <form:option value=""></form:option>
                                                    <form:options items="${payment_schedules}" itemLabel="name" itemValue="id" />
                                                </form:select>
                                                <form:errors path="payment.schedule.id" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="customer.person.firstName">Müştərinin adı</form:label>
                                                <form:input path="customer.person.firstName" cssClass="form-control" placeholder="Müştərinin adı" />
                                                <form:errors path="customer.person.firstName" cssClass="alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="customer.person.lastName">Müştərinin soyadı</form:label>
                                                <form:input path="customer.person.lastName" cssClass="form-control" placeholder="Müştərinin soyadı" />
                                                <form:errors path="customer.person.lastName" cssClass="alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="canavasser.id">Canavasser</form:label>
                                                <form:select  path="canavasser.id" cssClass="custom-select form-control" multiple="single">
                                                    <form:option value=""></form:option>
                                                    <c:forEach var="itemGroup" items="${employees}" varStatus="itemGroupIndex">
                                                        <optgroup label="${itemGroup.key}">
                                                            <form:options items="${itemGroup.value}" itemLabel="person.fullName" itemValue="id"/>
                                                        </optgroup>
                                                    </c:forEach>
                                                </form:select>
                                                <form:errors path="canavasser.id" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="dealer.id">Diller</form:label>
                                                <form:select  path="dealer.id" cssClass="custom-select form-control" multiple="single">
                                                    <form:option value=""></form:option>
                                                    <c:forEach var="itemGroup" items="${employees}" varStatus="itemGroupIndex">
                                                        <optgroup label="${itemGroup.key}">
                                                            <form:options items="${itemGroup.value}" itemLabel="person.fullName" itemValue="id"/>
                                                        </optgroup>
                                                    </c:forEach>
                                                </form:select>
                                                <form:errors path="dealer.id" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="console.id">Konsul</form:label>
                                                <form:select  path="console.id" cssClass="custom-select form-control">
                                                    <form:option value=""></form:option>
                                                    <c:forEach var="itemGroup" items="${employees}" varStatus="itemGroupIndex">
                                                        <optgroup label="${itemGroup.key}">
                                                            <form:options items="${itemGroup.value}" itemLabel="person.fullName" itemValue="id"/>
                                                        </optgroup>
                                                    </c:forEach>
                                                </form:select>
                                                <form:errors path="console.id" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="vanLeader.id">Van lider</form:label>
                                                <form:select  path="vanLeader.id" cssClass="custom-select form-control" multiple="single">
                                                    <form:option value=""></form:option>
                                                    <c:forEach var="itemGroup" items="${employees}" varStatus="itemGroupIndex">
                                                        <optgroup label="${itemGroup.key}">
                                                            <form:options items="${itemGroup.value}" itemLabel="person.fullName" itemValue="id"/>
                                                        </optgroup>
                                                    </c:forEach>
                                                </form:select>
                                                <form:errors path="vanLeader.id" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="row" style="padding-top: 30px;">
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <label class="kt-checkbox kt-checkbox--brand">
                                                            <form:checkbox path="payment.cash"/> Nağdlar
                                                            <span></span>
                                                        </label>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <label class="kt-checkbox kt-checkbox--brand">
                                                            <form:checkbox path="saled"/> Satılanlar
                                                            <span></span>
                                                        </label>
                                                    </div>
                                                </div>
                                                <div class="col-md-3">
                                                    <div class="form-group">
                                                        <label class="kt-checkbox kt-checkbox--brand">
                                                            <form:checkbox path="approve"/> Təsdiq edilməyənlər
                                                            <span></span>
                                                        </label>
                                                    </div>
                                                </div>
                                                <div class="col-md-3">
                                                    <div class="form-group">
                                                        <label class="kt-checkbox kt-checkbox--brand">
                                                            <form:checkbox path="returned"/> Qaytarılanlar
                                                            <span></span>
                                                        </label>
                                                    </div>
                                                </div>
                                                <c:if test="${delete.status}">
                                                    <div class="col-md-2">
                                                        <div class="form-group">
                                                            <label class="kt-checkbox kt-checkbox--brand">
                                                                <form:checkbox path="active"/> Aktual
                                                                <span></span>
                                                            </label>
                                                        </div>
                                                    </div>
                                                </c:if>
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
                            <c:set var="export" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'export')}"/>
                            <c:set var="view1" value="${utl:checkOperation(sessionScope.user.userModuleOperations, 'contact-history', 'view')}"/>
                            <c:set var="view2" value="${utl:checkOperation(sessionScope.user.userModuleOperations, 'schedule', 'view')}"/>
                            <c:set var="view3" value="${utl:checkOperation(sessionScope.user.userModuleOperations, 'service-regulator', 'view')}"/>
                            <c:set var="view4" value="${utl:checkOperation(sessionScope.user.userModuleOperations, 'customer', 'view')}"/>
                            <c:set var="view5" value="${utl:checkOperation(sessionScope.user.userModuleOperations, 'invoice', 'view')}"/>
                            <c:set var="view6" value="${utl:checkOperation(sessionScope.user.userModuleOperations, 'inventory', 'view')}"/>
                            <c:set var="approve" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'approve')}"/>
                            <c:set var="return1" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'return')}"/>
                            <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
                            <c:set var="export" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'export')}"/>
                            <c:set var="consolidate" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'consolidate')}"/>
                            <c:set var="approver" value="${utl:checkApprover(sessionScope.user)}"/>
                            <c:set var="canviewall" value="${utl:canViewAll(sessionScope.organization_selected)}"/>
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="group_table">
                                <thead>
                                <tr>
                                    <th>Kod</th>
                                    <th>İnventar</th>
                                    <th>Status</th>
                                    <c:if test="${canviewall}">
                                        <th>Struktur</th>
                                    </c:if>
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
                                    <tr data="<c:out value="${t.id}" />" cash="<c:out value="${t.payment.cash}" />" style="<c:out value="${t.saled?'background-color: rgb(237, 239, 255) !important':''}"/> <c:out value="${t.returned?'background-color: #FFCABA !important':''}"/>">
                                        <td style="<c:out value="${t.payment.cash?'background-color: #e6ffe7 !important':'background-color: #ffeaf1 !important'}"/>">
                                            <a href="javascript:copyToClipboard('<c:out value="${t.id}" />')" class="kt-link kt-font-lg kt-font-bold kt-margin-t-5"><c:out value="${t.id}"/></a>
                                        </td>
                                        <th>
                                            <c:forEach var="p" items="${t.salesInventories}" varStatus="lp">
                                                <c:out value="${p.inventory.id}" />.&nbsp;
                                                <c:out value="${p.inventory.name}" />&nbsp;&nbsp;
                                                <c:out value="${p.inventory.barcode}" /><br/>
                                            </c:forEach>
                                        </th>
                                        <td><c:out value="${t.approve?'Təsdiqlənənlər':'Təsdiqlənməyənlər'}"/></td>
                                        <c:if test="${canviewall}">
                                            <td><c:out value="${t.organization.name}"/></td>
                                        </c:if>
                                        <td>
                                            <fmt:formatDate value = "${t.saleDate}" pattern = "dd.MM.yyyy" /></td>
                                        <th>
                                            <c:if test="${not empty t.customer.id}">
                                                <a href="javascript:copyToClipboard2('<c:out value="${t.customer.id}" />', 'Müştəri kodu <b><c:out value="${t.customer.id}" /></b> kopyalandı')" class="kt-font-lg kt-font-bold kt-font-info kt-font-hover-danger pl-2 pr-2"><i class="la la-copy"></i></a>
                                            </c:if>
                                            <c:choose>
                                                <c:when test="${view4.status}">
                                                    <a href="javascript:window.open('/crm/customer/<c:out value="${t.customer.id}"/>', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder"><c:out value="${t.customer.person.fullName}"/></a>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:out value="${t.customer.person.fullName}"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </th>
                                        <th>
                                            Qiymət: <c:out value="${t.payment.price}" /><br/>
                                            <c:if test="${not empty t.payment.discount}">
                                                Endirim: <c:out value="${t.payment.discount}" /><br/>
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
                                                    <a href="javascript:window.open('/sale/invoice/<c:out value="${t.id}"/>', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-lg kt-font-bolder"><c:out value="${payable}"/> AZN</a>
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
                                            Zəmanət bitir: <fmt:formatDate value = "${t.guaranteeExpire}" pattern = "dd.MM.yyyy" /><br/>
                                            <c:if test="${not empty t.payment.gracePeriod and t.payment.gracePeriod gt 0}">
                                                Güzəşt müddəti: <c:out value="${t.payment.gracePeriod}" /> AY<br/>
                                            </c:if>
                                            <c:if test="${not empty t.taxConfiguration.voen}">
                                                VÖEN: <c:out value="${t.taxConfiguration.voen}" />
                                                <c:if test="${t.returned and not empty t.returnedDate}">
                                                    <br/>
                                                </c:if>
                                            </c:if>
                                            <c:if test="${t.returned and not empty t.returnedDate}">
                                                Qytarılıb: <fmt:formatDate value = "${t.returnedDate}" pattern = "dd.MM.yyyy" />
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:if test="${not empty t.console.person.fullName}">
                                                Konsul: <c:out value="${t.console.person.fullName}" /><br/>
                                            </c:if>
                                            <c:if test="${not empty t.vanLeader.person.fullName}">
                                                Ven lider: <c:out value="${t.vanLeader.person.fullName}" /><br/>
                                            </c:if>
                                            <c:if test="${not empty t.dealer.person.fullName}">
                                                Diller: <c:out value="${t.dealer.person.fullName}" /><br/>
                                            </c:if>
                                            <c:if test="${not empty t.canavasser.person.fullName}">
                                                Canvasser: <c:out value="${t.canavasser.person.fullName}" />
                                            </c:if>
                                        </td>
                                        <td nowrap class="text-center">
                                            <c:if test="${approve.status and !t.approve}">
                                                <a href="javascript:approveData($('#approve-form'),'<c:out value="${t.id}" /> nömrəli müqavilənin təsdiqi', '<c:out value="${t.id}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${approve.object.name}"/>">
                                                    <i class="<c:out value="${approve.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${view.status}">
                                                <a href="javascript:sales('view', $('#form'), '<c:out value="${t.id}" />', 'modal-operation', '<c:out value="${view.object.name}" />', '<c:out value="${t.customer.person.id}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${view.object.name}"/>">
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
                                                        <i class="flaticon-whatsapp"></i> Qeydlər
                                                    </a>
                                                    </c:if>
                                                    <c:if test="${view2.status and !t.payment.cash}">
                                                    <a href="/sale/schedule/<c:out value="${t.id}"/>" class="dropdown-item">
                                                        <i class="flaticon-folder-1"></i> Ödəniş qrafiki
                                                    </a>
                                                    </c:if>
                                                    <c:if test="${edit.status and t.approve and approver}">
                                                    <a href="javascript:sales('edit', $('#payment-form'), '<c:out value="${t.id}" />', 'payment-modal-operation', 'Ödəniş redaktə - Satış No: <c:out value="${t.id}"/>', '<c:out value="${t.customer.person.id}" />');" class="dropdown-item" title="<c:out value="${edit.object.name}"/>">
                                                        <i class="<c:out value="${edit.object.icon}"/>"></i> Ödəniş redaktə
                                                    </a>
                                                    </c:if>
                                                    <c:if test="${edit.status and t.approve and approver}">
                                                    <a href="javascript:changeInventory($('#change-inventory-form'), 'change-inventory-modal-operation', 'İnventar dəyişimi - Satış No: <c:out value="${t.id}"/>', '<c:out value="${t.id}" />', '<c:out value="${t.salesInventories[0].inventory.barcode}" />', '<c:out value="${t.salesInventories[0].inventory.name}" />');" class="dropdown-item" title="<c:out value="${edit.object.name}"/>">
                                                        <i class="<c:out value="${edit.object.icon}"/>"></i> İnventarı dəyiş
                                                    </a>
                                                    </c:if>
                                                    <c:if test="${edit.status and t.approve and approver}">
                                                    <a href="javascript:sales('edit', $('#sales-employee-form'), '<c:out value="${t.id}" />', 'sales-employee-modal-operation', 'Satış əməkdaşları redaktə - Satış No: <c:out value="${t.id}"/>', '<c:out value="${t.customer.person.id}" />');" class="dropdown-item" title="<c:out value="${edit.object.name}"/>">
                                                        <i class="<c:out value="${edit.object.icon}"/>"></i> Satış əməkdaşları redaktə
                                                    </a>
                                                    </c:if>
                                                    <c:if test="${view3.status}">
                                                    <a href="/sale/service-regulator/<c:out value="${t.id}"/>" class="dropdown-item">
                                                        <i class="flaticon-folder-2"></i> Servis requlyatoru
                                                    </a>
                                                    </c:if>
                                                    <c:if test="${consolidate.status}">
                                                    <a href="javascript:setTaxConfiguration($('#tax-configuration-form'), '<c:out value="${t.id}"/>', '<c:out value="${not empty t.taxConfiguration.id?t.taxConfiguration.id:0}"/>', 'tax-configuration-modal-operation', 'VÖEN təhkim et');" class="dropdown-item" title="<c:out value="${consolidate.object.name}"/>">
                                                        <i class="<c:out value="${consolidate.object.icon}"/>"></i> VÖEN təhkim et
                                                    </a>
                                                    </c:if>
                                                    <c:if test="${return1.status and t.approve}">
                                                    <a href="javascript:returnOper($('#return-form'), '<c:out value="${t.id}"/>', 'return-modal-operation', '<c:out value="${return1.object.name}"/> - Satış No: <c:out value="${t.id}"/>');getInventoriesForReturn('<c:out value="${t.id}" />', $('#return-data-repeater-list'));" class="dropdown-item" title="<c:out value="${return1.object.name}"/>">
                                                        <i class="<c:out value="${return1.object.icon}"/>"></i> <c:out value="${return1.object.name}"/>
                                                    </a>
                                                    </c:if>
                                                    <c:if test="${edit.status and !t.approve}">
                                                    <a href="javascript:sales('edit', $('#form'), '<c:out value="${t.id}" />', 'modal-operation', '<c:out value="${edit.object.name}" />', '<c:out value="${t.customer.person.id}" />');" class="dropdown-item" title="<c:out value="${edit.object.name}"/>">
                                                        <i class="<c:out value="${edit.object.icon}"/>"></i> <c:out value="${edit.object.name}"/>
                                                    </a>
                                                    </c:if>
                                                    <c:if test="${delete.status and !t.approve}">
                                                    <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.id}" /> <br/> <c:out value="${t.customer.person.fullName}" />');" class="dropdown-item" title="<c:out value="${delete.object.name}"/>">
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
                <form:form modelAttribute="form" id="form" method="post" action="/sale/sales" cssClass="form-group kt-form"  enctype="multipart/form-data">
                    <form:hidden path="id"/>
                    <form:hidden path="active"/>
                    <form:hidden path="service"/>
                    <form:hidden path="returned"/>
                    <form:hidden path="saled"/>
                    <form:hidden path="organization.id"/>
                    <input type="hidden" name="customer.organization" value="<c:out value="${sessionScope.organization.id}"/>"/>
                    <div class="kt-wizard-v1__content" data-ktwizard-type="step-content" data-ktwizard-state="current">
                        <div class="kt-form__section kt-form__section--first">
                            <div class="kt-wizard-v1__form">
                                <div class="row">
                                    <div class="col-sm-3">
                                        <form:label path="saleDate">Satış tarixi</form:label>
                                        <div class="form-group">
                                            <div class="input-group date" >
                                                <div class="input-group-prepend"><span class="input-group-text"><i class="la la-calendar"></i></span></div>
                                                <form:input path="saleDate" autocomplete="off" date_="date_" cssClass="form-control datepicker-element" placeholder="dd.MM.yyyy"/>
                                            </div>
                                            <form:errors path="saleDate" cssClass="control-label alert-danger" />
                                        </div>
                                    </div>
                                    <div class="col-sm-2">
                                        <form:label path="guarantee">Zəmanət müddəti</form:label>
                                        <form:select  path="guarantee" cssClass="custom-select form-control">
                                            <form:options items="${guarantees}" itemLabel="name" itemValue="attr1" />
                                        </form:select>
                                        <form:errors path="guarantee" cssClass="control-label alert-danger"/>
                                    </div>
                                    <div class="col-sm-2">
                                        <form:label path="payment.price">Qiymət</form:label>
                                        <div class="input-group" >
                                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-usd"></i></span></div>
                                            <form:input path="payment.price" onchange="calculate($('#form'), $(this), 'lastPriceLabel')" cssClass="form-control" placeholder="Qiyməti daxil edin"/>
                                        </div>
                                        <form:errors path="payment.price" cssClass="control-label alert-danger"/>
                                    </div>
                                    <div class="col-sm-3 text-center">
                                        <div class="row">
                                            <div class="col-sm-6">
                                                <div class="form-group mt-3 pt-4">
                                                    <label class="kt-checkbox kt-checkbox--brand">
                                                        <form:checkbox path="payment.cash" onclick="doCash($(this), $('#form'), 'credit-div')"/> Nağdırmı?
                                                        <span></span>
                                                    </label>
                                                </div>
                                            </div>
                                            <div class="col-sm-6">
                                                <div class="form-group mt-3 pt-4">
                                                    <label class="kt-checkbox kt-checkbox--brand">
                                                        <form:checkbox path="payment.hasDiscount" onclick="doDiscount($(this), '10%', $('#form'), 1, 'lastPriceLabel')"/> Endirim varmı?
                                                        <span></span>
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-2">
                                        <div class="form-group">
                                            <form:label path="payment.discount">Endirim dəyəri</form:label>
                                            <form:input path="payment.discount" cssClass="form-control" readonly="true" cssStyle="text-align: -webkit-center; text-align: center; font-weight: bold; letter-spacing: 3px;"/>
                                            <form:errors path="payment.discount" cssClass="control-label alert-danger"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-5">
                                        <div class="kt_repeater_1">
                                            <div class="form-group form-group-last row" id="kt_repeater_2">
                                                <div data-repeater-list="" class="col-lg-12" id="data-repeater-list">
                                                    <div data-repeater-item class="form-group row align-items-center">
                                                        <div class="col-9">
                                                            <div class="kt-form__group--inline">
                                                                <div class="kt-form__label">
                                                                    <label>İnventar:</label>
                                                                </div>
                                                                <div class="kt-form__control">
                                                                    <div class="row">
                                                                        <div class="col-7">
                                                                            <input type="text" attr="barcode" name="inventory.barcode" class="form-control" placeholder="Barkodu daxil edin..." onchange="findInventory($(this))">
                                                                            <label attr="name" name="inventory.name"></label>
                                                                            <input type="hidden" attr="id" name="inventory.id" class="form-control">
                                                                        </div>
                                                                        <div class="col-5">
                                                                            <select attr="type" name="salesType" class="form-control" style="padding: 5px;">
                                                                                <c:forEach var="t" items="${sales_types}" varStatus="loop">
                                                                                    <option value="${t.id}">${t.name}</option>
                                                                                </c:forEach>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="d-md-none kt-margin-b-10"></div>
                                                        </div>
                                                        <div class="col-3">
                                                            <div>
                                                                <a href="javascript:;" data-repeater-delete="" class="btn-sm btn btn-label-danger btn-bold">
                                                                    <i class="la la-trash-o"></i>
                                                                    Sil
                                                                </a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group form-group-last row">
                                                <div class="col-6">
                                                    <a href="javascript:;" data-repeater-create="" class="btn btn-bold btn-sm btn-label-primary">
                                                        <i class="la la-plus"></i> Əlavə et
                                                    </a>
                                                </div>
                                                <c:if test="${view6.status}">
                                                    <div class="col-6 text-right">
                                                        <a href="javascript:window.open('/warehouse/inventory', 'mywindow', 'width=1250, height=800')" data-repeater-delete="" class="btn-sm btn btn-label-success btn-bold">
                                                            <i class="la la-question"></i>
                                                            İnventar
                                                        </a>
                                                    </div>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-7">
                                        <div class="form-group">
                                            <form:label path="payment.description">Açıqlama</form:label>
                                            <form:textarea path="payment.description" cssClass="form-control" rows="3"/>
                                            <form:errors path="payment.description" cssClass="control-label alert-danger"/>
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
                                                    <div class="col-sm-4">
                                                        <div class="form-group">
                                                            <form:label path="payment.schedule.id">Ödəniş qrafiki</form:label>
                                                            <form:select  path="payment.schedule.id" cssClass="custom-select form-control">
                                                                <form:options items="${payment_schedules}" itemLabel="name" itemValue="id" />
                                                            </form:select>
                                                            <form:errors path="payment.schedule.id" cssClass="control-label alert-danger"/>
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-4">
                                                        <div class="form-group">
                                                            <form:label path="payment.period.id">Ödəniş edilsin</form:label>
                                                            <form:select  path="payment.period.id" cssClass="custom-select form-control">
                                                                <form:options items="${payment_periods}" itemLabel="name" itemValue="id" />
                                                            </form:select>
                                                            <form:errors path="payment.period.id" cssClass="control-label alert-danger"/>
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-4">
                                                        <div class="form-group">
                                                            <form:label path="payment.gracePeriod">Güzəşt müd./AY</form:label>
                                                            <div class="input-group" >
                                                                <div class="input-group-prepend"><span class="input-group-text"><i class="la la-calendar"></i></span></div>
                                                                <form:input path="payment.gracePeriod" cssClass="form-control" placeholder="Daxil edin"/>
                                                            </div>
                                                            <form:errors path="payment.gracePeriod" cssClass="control-label alert-danger"/>
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
                                                <button type="button" class="btn btn-outline-info btn-tallest" style="font-size: 15px;font-weight: bolder; padding-left: 7px; padding-right: 8px;" onclick="schedule($('#form'), 'schedule-div', 'schedule-table')"><i class="fa fa-play"></i> Qrafik məbləğini hesabla</button>
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
                                                <form:label path="customer.id">Müştəri kodu</form:label>
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text" style="background-color: white; border-right: none;"><i class="la la-search"></i></span>
                                                    </div>
                                                    <form:input path="customer.id" autocomplete="false" class="form-control" placeholder="Müştəri kodunu daxil edin..." style="border-left: none;" />
                                                    <div class="input-group-append">
                                                        <button class="btn btn-primary" type="button" onclick="findCustomer($('#form'), $('#form').find('input[name=\'customer.id\']'))">Müştərini axtar</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <c:if test="${view4.status}">
                                            <div class="col-md-6 text-right">
                                                <div class="form-group pt-4">
                                                    <form:label path="customer">&nbsp;</form:label>
                                                    <a href="javascript:window.open('/crm/customer', 'mywindow', 'width=1250, height=800')" data-repeater-delete="" class="btn-sm btn btn-label-success btn-bold">
                                                        <i class="la la-user"></i>
                                                        Müştərilərin siyahısı
                                                    </a>
                                                </div>
                                            </div>
                                        </c:if>
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
                                                <form:input path="customer.person.idCardPinCode" cssClass="form-control" cssStyle="text-transform: uppercase;" maxlength="7" placeholder="Məs: 4HWL0AM"/>
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
                                                    <input type="file" name="file1" class="custom-file-input" id="file1" accept="image/*">
                                                    <label class="custom-file-label" for="file1">Şəxsiyyət vəsiqəsi</label>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label>Ş.v-nin arxa hissəsi</label>
                                                <div></div>
                                                <div class="custom-file">
                                                    <input type="file" name="file2" class="custom-file-input" id="file2" accept="image/*">
                                                    <label class="custom-file-label" for="file2">Şəxsiyyət vəsiqəsi</label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row" id="image-content">

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
                                                <form:label path="customer.person.contact.city.id">Şəhər</form:label>
                                                <form:select  path="customer.person.contact.city.id" onchange="selectLivingCity($('#form'), $(this))" cssClass="custom-select form-control">
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
                                                <form:label path="customer.person.contact.livingCity.id">Yaşadığı şəhər</form:label>
                                                <form:select  path="customer.person.contact.livingCity.id" cssClass="custom-select form-control">
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
                                                <form:label path="console.id">Konsul</form:label>
                                                <form:select  path="console.id" cssClass="custom-select form-control">
                                                    <form:option value=""></form:option>
                                                    <c:forEach var="itemGroup" items="${employees}" varStatus="itemGroupIndex">
                                                        <optgroup label="${itemGroup.key}">
                                                            <form:options items="${itemGroup.value}" itemLabel="person.fullName" itemValue="id"/>
                                                        </optgroup>
                                                    </c:forEach>
                                                </form:select>
                                                <form:errors path="console.id" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-5">
                                            <div class="form-group">
                                                <form:label path="vanLeader.id">Van lider</form:label>
                                                <form:select  path="vanLeader.id" cssClass="custom-select form-control" multiple="single">
                                                    <form:option value=""></form:option>
                                                    <c:forEach var="itemGroup" items="${employees}" varStatus="itemGroupIndex">
                                                        <optgroup label="${itemGroup.key}">
                                                            <form:options items="${itemGroup.value}" itemLabel="person.fullName" itemValue="id"/>
                                                        </optgroup>
                                                    </c:forEach>
                                                </form:select>
                                                <form:errors path="vanLeader.id" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="kt-separator kt-separator--border-dashed kt-separator--space-sm kt-separator--portlet-fit" style="margin: 1rem 0"></div>
                                    <div class="row">
                                        <div class="col-md-5 offset-md-1">
                                            <div class="form-group">
                                                <form:label path="dealer.id">Diller</form:label>
                                                <form:select  path="dealer.id" cssClass="custom-select form-control" multiple="single">
                                                    <form:option value=""></form:option>
                                                    <c:forEach var="itemGroup" items="${employees}" varStatus="itemGroupIndex">
                                                        <optgroup label="${itemGroup.key}">
                                                            <form:options items="${itemGroup.value}" itemLabel="person.fullName" itemValue="id"/>
                                                        </optgroup>
                                                    </c:forEach>
                                                </form:select>
                                                <form:errors path="dealer.id" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-5">
                                            <div class="form-group">
                                                <form:label path="canavasser.id">Canavasser</form:label>
                                                <form:select  path="canavasser.id" cssClass="custom-select form-control" multiple="single">
                                                    <form:option value=""></form:option>
                                                    <c:forEach var="itemGroup" items="${employees}" varStatus="itemGroupIndex">
                                                        <optgroup label="${itemGroup.key}">
                                                            <form:options items="${itemGroup.value}" itemLabel="person.fullName" itemValue="id"/>
                                                        </optgroup>
                                                    </c:forEach>
                                                </form:select>
                                                <form:errors path="canavasser.id" cssClass="control-label alert-danger"/>
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

<div class="modal fade" id="return-modal-operation" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="return_form" id="return-form" method="post" action="/sale/sales/return" cssClass="form-group">
                    <form:hidden path="salesId"/>
                    <div class="kt_repeater_1">
                        <div class="form-group form-group-last row">
                            <div data-repeater-list="" class="col-lg-12" id="return-data-repeater-list">
                                <div data-repeater-item class="form-group row align-items-center">
                                    <div class="col-9">
                                        <div class="kt-form__group--inline">
                                            <div class="kt-form__label">
                                                <label>İnventar:</label>
                                            </div>
                                            <div class="kt-form__control">
                                                <div class="row">
                                                    <div class="col-7">
                                                        <input type="text" attr="barcode" name="inventory.barcode" class="form-control" placeholder="Barkodu daxil edin..." onchange="findInventory($(this))" readonly>
                                                        <label attr="name" name="inventory.name"></label>
                                                        <input type="hidden" attr="id" name="inventory.id" class="form-control">
                                                    </div>
                                                    <div class="col-5">
                                                        <select attr="type" name="salesType" class="form-control" style="padding: 5px;">
                                                            <c:forEach var="t" items="${sales_types}" varStatus="loop">
                                                                <option value="${t.id}">${t.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="d-md-none kt-margin-b-10"></div>
                                    </div>
                                    <div class="col-3">
                                        <div>
                                            <a href="javascript:;" data-repeater-delete="" class="btn-sm btn btn-label-danger btn-bold">
                                                <i class="la la-trash-o"></i>
                                                Sil
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group form-group-last row">
                            <div class="col-6">
                                <div class="form-group">
                                    <form:label path="returnPrice">Qaytarılacağ məbləğ</form:label>
                                    <div class="input-group" >
                                        <div class="input-group-prepend"><span class="input-group-text"><i class="la la-usd"></i></span></div>
                                        <form:input path="returnPrice" cssClass="form-control" placeholder="Məbləği daxil edin"/>
                                    </div>
                                    <form:errors path="returnPrice" cssClass="alert-danger control-label"/>
                                </div>
                            </div>
                            <c:if test="${view6.status}">
                                <div class="col-6 text-right">
                                    <label>&nbsp;</label>
                                    <div>
                                        <a href="javascript:window.open('/warehouse/inventory', 'mywindow', 'width=1250, height=800')" data-repeater-delete="" class="btn-sm btn btn-label-success btn-bold">
                                            <i class="la la-question"></i>
                                            İnventar
                                        </a>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="reason">Səbəbi</form:label>
                        <form:textarea path="reason" cssClass="form-control" placeholder="Daxil edin" rows="4"/>
                        <form:errors path="reason" cssClass="alert-danger control-label"/>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#return-form'));">Bəli, təsdiq edirəm!</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="payment-modal-operation" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="payment-form" method="post" action="/sale/sales/payment" cssClass="form-group">
                    <form:hidden path="id"/>
                    <div class="row">
                        <div class="col-sm-2">
                            <form:label path="saleDate">Satış tarixi</form:label>
                            <div class="form-group">
                                <div class="input-group date" >
                                    <div class="input-group-prepend" style="margin-left: -6px; margin-right: -11px"><span class="input-group-text"><i class="la la-calendar"></i></span></div>
                                    <form:input path="saleDate" autocomplete="off" date_="date_" cssClass="form-control" placeholder="dd.MM.yyyy" readonly="true"/>
                                </div>
                                <form:errors path="saleDate" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <form:label path="guarantee">Zəmanət müddəti</form:label>
                            <form:select  path="guarantee" cssClass="custom-select form-control">
                                <form:options items="${guarantees}" itemLabel="name" itemValue="attr1" />
                            </form:select>
                            <form:errors path="guarantee" cssClass="control-label alert-danger"/>
                        </div>
                        <div class="col-sm-2">
                            <form:label path="payment.price">Qiymət</form:label>
                            <div class="input-group" >
                                <div class="input-group-prepend"><span class="input-group-text"><i class="la la-usd"></i></span></div>
                                <form:input path="payment.price" onchange="calculate($('#payment-form'), $(this), 'lastPriceLabel2')" cssClass="form-control" placeholder="Qiyməti daxil edin"/>
                            </div>
                            <form:errors path="payment.price" cssClass="control-label alert-danger"/>
                        </div>
                        <div class="col-sm-2">
                            <div class="form-group mt-3 pt-4">
                                <label class="kt-checkbox kt-checkbox--brand">
                                    <form:checkbox path="payment.cash" onclick="doCash($(this), $('#payment-form'), 'credit-div-2')"/> Nağdırmı?
                                    <span></span>
                                </label>
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="form-group mt-3 pt-4">
                                <label class="kt-checkbox kt-checkbox--brand">
                                    <form:checkbox path="payment.hasDiscount" onclick="doDiscount($(this), '10%', $('#payment-form'), 2, 'lastPriceLabel2')"/> Endirim varmı?
                                    <span></span>
                                </label>
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="form-group">
                                <form:label path="payment.discount">Endirim dəyəri</form:label>
                                <form:input path="payment.discount" cssClass="form-control" readonly="true" cssStyle="text-align: -webkit-center; text-align: center; font-weight: bold; letter-spacing: 3px;"/>
                                <form:errors path="payment.discount" cssClass="control-label alert-danger"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <form:label path="payment.description">Açıqlama</form:label>
                                <form:textarea path="payment.description" cssClass="form-control" rows="3"/>
                                <form:errors path="payment.description" cssClass="control-label alert-danger"/>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="alert alert-info alert-elevate" role="alert" style="padding: 0; padding-left: 1rem; padding-right: 1rem; padding-top: 5px;">
                                        <div class="alert-icon"><i class="flaticon-warning kt-font-brand kt-font-light"></i></div>
                                        <div class="alert-text text-center">
                                            <div style="font-size: 15px; font-weight: bold; letter-spacing: 1px;">
                                                Yekun ödəniləcək məbləğ:
                                                <span id="lastPriceLabel2">0</span>
                                                <span> AZN</span>
                                                <form:hidden path="payment.lastPrice"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div  id="credit-div-2">
                                <div class="row animated zoomIn">
                                    <div class="col-md-12 offset-md-1">
                                        <div class="row">
                                            <div class="col-sm-2">
                                                <div class="form-group">
                                                    <form:label path="payment.schedule.id">Ödəniş qrafiki</form:label>
                                                    <form:select  path="payment.schedule.id" cssClass="custom-select form-control">
                                                        <form:options items="${payment_schedules}" itemLabel="name" itemValue="id" />
                                                    </form:select>
                                                    <form:errors path="payment.schedule.id" cssClass="control-label alert-danger"/>
                                                </div>
                                            </div>
                                            <div class="col-sm-2">
                                                <div class="form-group">
                                                    <form:label path="payment.period.id">Ödəniş edilsin</form:label>
                                                    <form:select  path="payment.period.id" cssClass="custom-select form-control">
                                                        <form:options items="${payment_periods}" itemLabel="name" itemValue="id" />
                                                    </form:select>
                                                    <form:errors path="payment.period.id" cssClass="control-label alert-danger"/>
                                                </div>
                                            </div>
                                            <div class="col-sm-2">
                                                <div class="form-group">
                                                    <form:label path="payment.gracePeriod">Güzəşt müddəti / AY</form:label>
                                                    <div class="input-group" >
                                                        <div class="input-group-prepend"><span class="input-group-text"><i class="la la-calendar"></i></span></div>
                                                        <form:input path="payment.gracePeriod" cssClass="form-control" placeholder="Daxil edin"/>
                                                    </div>
                                                    <form:errors path="payment.gracePeriod" cssClass="control-label alert-danger"/>
                                                </div>
                                            </div>
                                            <div class="col-sm-2">
                                                <div class="form-group">
                                                    <form:label path="payment.down">İlkin ödəniş</form:label>
                                                    <div class="input-group" >
                                                        <div class="input-group-prepend"><span class="input-group-text"><i class="la la-usd"></i></span></div>
                                                        <form:input path="payment.down" cssClass="form-control" placeholder="İlkin ödənişi daxil edin"/>
                                                    </div>
                                                    <form:errors path="payment.down" cssClass="alert-danger control-label"/>
                                                </div>
                                            </div>
                                            <div class="col-sm-2">
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
                                </div>

                                <div class="row">
                                    <div class="col-sm-12 text-center">
                                        <label>&nbsp;</label>
                                        <button type="button" class="btn btn-outline-info btn-tallest" style="font-size: 15px;font-weight: bolder; padding-left: 7px; padding-right: 8px;" onclick="schedule($('#payment-form'), 'schedule-div-2', 'schedule-table-2')"><i class="fa fa-play"></i> Qrafik məbləğini hesabla</button>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-12" id="schedule-div-2">

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#payment-form'));">Yadda saxla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="sales-employee-modal-operation" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="sales-employee-form" method="post" action="/sale/sales/employee" cssClass="form-group">
                    <form:hidden path="id"/>
                    <div class="form-group">
                        <form:label path="console.id">Konsul</form:label>
                        <form:select  path="console.id" cssClass="custom-select form-control">
                            <form:option value=""></form:option>
                            <c:forEach var="itemGroup" items="${employees}" varStatus="itemGroupIndex">
                                <optgroup label="${itemGroup.key}">
                                    <form:options items="${itemGroup.value}" itemLabel="person.fullName" itemValue="id"/>
                                </optgroup>
                            </c:forEach>
                        </form:select>
                        <form:errors path="console.id" cssClass="control-label alert-danger"/>
                    </div>
                    <div class="form-group">
                        <form:label path="vanLeader.id">Van lider</form:label>
                        <form:select  path="vanLeader.id" cssClass="custom-select form-control" multiple="single">
                            <form:option value=""></form:option>
                            <c:forEach var="itemGroup" items="${employees}" varStatus="itemGroupIndex">
                                <optgroup label="${itemGroup.key}">
                                    <form:options items="${itemGroup.value}" itemLabel="person.fullName" itemValue="id"/>
                                </optgroup>
                            </c:forEach>
                        </form:select>
                        <form:errors path="vanLeader.id" cssClass="control-label alert-danger"/>
                    </div>
                    <div class="form-group">
                        <form:label path="dealer.id">Diller</form:label>
                        <form:select  path="dealer.id" cssClass="custom-select form-control" multiple="single">
                            <form:option value=""></form:option>
                            <c:forEach var="itemGroup" items="${employees}" varStatus="itemGroupIndex">
                                <optgroup label="${itemGroup.key}">
                                    <form:options items="${itemGroup.value}" itemLabel="person.fullName" itemValue="id"/>
                                </optgroup>
                            </c:forEach>
                        </form:select>
                        <form:errors path="dealer.id" cssClass="control-label alert-danger"/>
                    </div>
                    <div class="form-group">
                        <form:label path="canavasser.id">Canavasser</form:label>
                        <form:select  path="canavasser.id" cssClass="custom-select form-control" multiple="single">
                            <form:option value=""></form:option>
                            <c:forEach var="itemGroup" items="${employees}" varStatus="itemGroupIndex">
                                <optgroup label="${itemGroup.key}">
                                    <form:options items="${itemGroup.value}" itemLabel="person.fullName" itemValue="id"/>
                                </optgroup>
                            </c:forEach>
                        </form:select>
                        <form:errors path="canavasser.id" cssClass="control-label alert-danger"/>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#sales-employee-form'));">Yadda saxla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="tax-configuration-modal-operation" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="tax-configuration-form" method="post" action="/sale/sales/tax-configuration" cssClass="form-group">
                    <form:hidden path="id"/>
                    <div class="form-group">
                        <form:label path="taxConfiguration.id">VÖEN</form:label>
                        <form:select path="taxConfiguration.id" cssClass="form-control">
                            <form:option value=""/>
                            <form:options items="${tax_configurations}" itemValue="id" itemLabel="label"/>
                        </form:select>
                        <form:errors path="taxConfiguration.id" cssClass="alert-danger control-label"/>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#tax-configuration-form'));">Yadda saxla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="change-inventory-modal-operation" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="change_inventory_form" id="change-inventory-form" method="post" action="/sale/sales/change-inventory" cssClass="form-group">
                    <form:hidden path="salesId"/>
                    <div class="form-group">
                        <form:label path="oldInventoryBarcode">Köhnə barkod</form:label>
                        <div class="input-group" >
                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-calendar"></i></span></div>
                            <form:input path="oldInventoryBarcode" cssClass="form-control" placeholder="Daxil edin" onchange="findInventory3($(this), $('#change-inventory-form'), $('#change-inventory-form').find('#oldInventoryName'))"/>
                        </div>
                        <form:errors path="oldInventoryBarcode" cssClass="alert-danger control-label"/>
                        <label id="oldInventoryName" class="font-weight-bold kt-font-danger text-center" style="width: 100%"></label>
                    </div>
                    <div class="form-group">
                        <form:label path="newInventoryBarcode">Yeni barkod</form:label>
                        <div class="input-group" >
                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-calendar"></i></span></div>
                            <form:input path="newInventoryBarcode" cssClass="form-control" placeholder="Daxil edin" onchange="findInventory3($(this), $('#change-inventory-form'), $('#change-inventory-form').find('#newInventoryName'))"/>
                        </div>
                        <form:errors path="newInventoryBarcode" cssClass="alert-danger control-label"/>
                        <c:if test="${view6.status}">
                            <div class="text-right">
                                <a href="javascript:window.open('/warehouse/inventory', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-sm kt-font-bold kt-margin-t-5">
                                    <i class="la la-question"></i>
                                    İnventarların siyahısı
                                </a>
                            </div>
                        </c:if>
                        <label id="newInventoryName" class="font-weight-bold kt-font-danger text-center" style="width: 100%"></label>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#change-inventory-form'));">Yadda saxla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
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

<script src="<c:url value="/assets/vendors/general/typeahead.js/dist/typeahead.bundle.js" />" type="text/javascript"></script>
<script src="<c:url value="/assets/vendors/general/typeahead.js/dist/typeahead.jquery.js" />" type="text/javascript"></script>

<script>
    $('#group_table').DataTable({
        <c:if test="${export.status}">
        dom: 'B<"clear">lfrtip',
        buttons: [
               $.extend( true, {}, buttonCommon, {
                    extend: 'copyHtml5'
                } ),
                $.extend( true, {}, buttonCommon, {
                    extend: 'csvHtml5'
                } ),
                $.extend( true, {}, buttonCommon, {
                    extend: 'excelHtml5'
                } ),
                $.extend( true, {}, buttonCommon, {
                    extend: 'pdfHtml5'
                } ),
                $.extend( true, {}, buttonCommon, {
                    extend: 'print'
                } )
        ],
        </c:if>
        responsive: true,
        fixedHeader: {
            headerOffset: $('#kt_header').outerHeight()
        },
        pageLength: 100,
        order: [[2, 'asc']],
        drawCallback: function(settings) {
            var api = this.api();
            var rows = api.rows({page: 'current'}).nodes();
            var last = null;

            api.column(2, {page: 'current'}).data().each(function(group, i) {
                if (last !== group) {
                    $(rows).eq(i).before(
                        '<tr class="group"><td colspan="30">' + group + '</td></tr>'
                    );
                    last = group;
                }
            });
        },
        columnDefs: [
            {
                targets: [2],
                visible: false
            }
        ]
    });

    function schedule(form, divId, tableId){
        var table='';
        swal.fire({
            text: 'Proses davam edir...',
            allowOutsideClick: false,
            onOpen: function() {
                swal.showLoading();
                $.ajax({
                    url: '/sale/payment/schedule/' + $(form).find("input[name='payment.lastPrice']").val() + '/' + $(form).find("input[name='payment.down']").val() + '/' + $(form).find("select[name='payment.schedule.id']").val() + '/' + $(form).find("select[name='payment.period.id']").val() + '/' + $(form).find("input[name='payment.gracePeriod']").val() + '/' + $(form).find("input[name='saleDate']").val(),
                    type: 'GET',
                    dataType: 'json',
                    beforeSend: function() {
                        table+="<table class='table table-striped- table-bordered table-hover table-checkable  animated zoomIn' id='"+tableId+"'><thead><th style='width: 20px;' class='text-center'>№</th><th>Ödəniş tarixi</th><th>Ödəniş məbləği</th></thead>"
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
                        $("#"+divId).html(table);
                        $("#"+tableId).DataTable({pageLength: 10});
                    }
                })
            }
        });

    }
    function calculate(form, element, lastPriceLabelId){
        var price = $(element).val();
        var discount = $(form).find("input[name='payment.discount']").val();
        if(discount.trim().length>0){
            var discounts = discount.trim().split("%");
            if(discounts.length>1){
                price = price-price*parseFloat(discounts[0])*0.01;
            } else {
                price = price-parseFloat(discounts[0]);
            }
            price = Math.ceil(price);
        }
        $("#"+lastPriceLabelId).text(price);
        $(form).find("input[name='payment.lastPrice']").val(price);
    }

    $(function(){
        $("input[name='payment.price']").change();
    });

    function doCash(element, form, divId){
        if($(element).is(":checked")){
            $("#"+divId).addClass("kt-hidden");
        } else {
            $(form).find("input[name='payment.discount']").val('');
            $(form).find("input[name='payment.description']").val('');
            $("#"+divId).removeClass("kt-hidden");

        }
    }

    function doDiscount(element, defaultCash, form, index, lastPriceLabel){
        if($(element).is(":checked")){
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
                    name: 'sale-value-'+index,
                    style: 'text-align: -webkit-center; text-align: center; font-weight: bold; letter-spacing: 3px; z-index: 100000'
                },
                footer: '<a href>Məlumatlar yenilənsinmi?</a>'
            }).then(function(result) {
                $(form).find("input[name='payment.discount']").val('');
                $(form).find("input[name='payment.description']").val('');
                if (result.value) {
                    $(form).find("input[name='payment.discount']").val($("input[name='sale-value-"+index+"']").val());
                    $(form).find("input[name='payment.price']").change();
                    if($("input[name='sale-value-"+index+"']").val()!==defaultCash){
                        swal.fire({
                            title: $("input[name='sale-value-"+index+"']").val()+' - Səbəbini daxil edin',
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
                                $(form).find("textarea[name='payment.description']").val(result2.value);
                            }
                        })

                    }
                }
            })

        } else {
            var price = $(form).find("input[name='payment.price']").val();
            $("#" + lastPriceLabel).text(price);
            $(form).find("input[name='payment.lastPrice']").val(price);
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
                    'customer.id': {
                        required: false,
                        digits: true
                    },
                    'customer.person.firstName': {
                        required: true
                    },
                    'customer.person.lastName': {
                        required: true
                    },
                    'customer.person.idCardPinCode': {
                        required: true,
                        maxlength: 7,
                        minlength: 7
                    },
                    'customer.person.contact.mobilePhone': {
                        required: true
                    },
                    'saleDate': {
                        required: true,
                    },
                    'customer.person.contact.city.id': {
                        required: true
                    },
                    'customer.person.contact.livingCity.id': {
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
                    'salesInventories[1].inventory.name': {
                        required: true
                    },
                    'salesInventories[1].inventory.barcode': {
                        required: true
                    },
                    'salesInventories[2].inventory.name': {
                        required: true
                    },
                    'salesInventories[2].inventory.barcode': {
                        required: true
                    },
                    'payment.price': {
                        required: false,
                        number: true
                        <c:if test="${prices.size() gt 0}">
                        ,pattern: /^(1499|1599|1699)$/
                        </c:if>
                    },
                    'payment.gracePeriod': {
                        required: true,
                        number: true,
                        pattern: /^(0|1)$/
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
                    'console.id': {
                        required: true
                    },
                    'vanLeader.id': {
                        required: true
                    },
                    'dealer.id': {
                        required: true
                    },
                    'canavasser.id': {
                        required: true
                    },
                },
                messages: {
                    'payment.price': "1499, 1599 və ya 1699 ola bilər",
                    'payment.gracePeriod': "0 və ya 1 (~30 gün) ay ola bilər"
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
                                $(form).find("select[name='customer.person.contact.city.id'] option[value="+customer.person.contact.city.id+"]").attr("selected", "selected");
                            }
                            $(form).find("input[name='customer.person.contact.address']").val(customer.person.contact.address);
                            if(customer.person.contact.livingCity!=null){
                                $(form).find("select[name='customer.person.contact.livingCity.id'] option[value="+customer.person.contact.livingCity.id+"]").attr("selected", "selected");
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

    function getInventoriesForReturn(salesId, repeater){
        var content = '';
        swal.fire({
            text: 'Proses davam edir...',
            allowOutsideClick: false,
            onOpen: function() {
                swal.showLoading();
                $.ajax({
                    url: '/sale/api/service/inventory/'+salesId,
                    type: 'GET',
                    dataType: 'json',
                    beforeSend: function() {
                        $(".kt_repeater_1").find(repeater).html(content);
                    },
                    success: function(data) {
                        console.log(data);

                        $.each(data, function( index, value ) {
                            //console.log(value.salesType.id)
                            content='<div data-repeater-item="" class="form-group row align-items-center">\n' +
                                '                                            <div class="col-9">\n' +
                                '                                                <div class="kt-form__group--inline">\n' +
                                '                                                    <div class="kt-form__label">\n' +
                                '                                                        <label>İnventar:</label>\n' +
                                '                                                    </div>\n' +
                                '                                                    <div class="kt-form__control">\n' +
                                '                                                       <div class="row">\n' +
                                '                                                            <div class="col-5">\n' +
                                '                                                                <input type="text" attr="barcode" name="barcode" name="salesInventories['+index+'].inventory.barcode" class="form-control" placeholder="Barkodu daxil edin..." onchange="findInventory($(this))" value="'+value.inventory.barcode+'" readonly>\n' +
                                '                                                                <label attr="name" name="salesInventories['+index+'].inventory.name">'+value.inventory.name+'</label>\n' +
                                '                                                                <input type="hidden" attr="id" name="salesInventories['+index+'].inventory.id" class="form-control" value="'+value.inventory.id+'">\n' +
                                '                                                            </div>\n' +
                                '                                                            <div class="col-4">\n' +
                                '                                                                <select attr="type" name="salesInventories['+index+'].salesType" class="form-control" style="padding: 5px;">\n' +
                                '                                                                    <c:forEach var="t" items="${sales_types}" varStatus="loop">\n' +
                                '                                                                        <option value="${t.id}">${t.name}</option>\n' +
                                '                                                                    </c:forEach>\n' +
                                '                                                                </select>\n' +
                                '                                                            </div>\n' +
                                '                                                            <div class="col-3 pt-2">\n' +
                                '                                                               <label class="kt-checkbox kt-checkbox--brand">\n' +
                                '                                                                   <input type="checkbox" attr="old" name="salesInventories['+index+'].inventory.old" class="form-control" value="true"> Köhnədir?\n' +
                                '                                                                   <span></span>\n' +
                                '                                                               </label>\n' +
                                '                                                            </div>\n' +
                                '                                                        </div>' +
                                '                                                    </div>\n' +
                                '                                                </div>\n' +
                                '                                                <div class="d-md-none kt-margin-b-10"></div>\n' +
                                '                                            </div>\n' +
                                '                                            <div class="col-3 text-center">\n' +
                                '                                                <div>\n' +
                                '                                                    <a href="javascript:;" data-repeater-delete="" class="btn-sm btn btn-label-danger btn-bold">\n' +
                                '                                                        <i class="la la-trash-o"></i>\n' +
                                '                                                        Sil\n' +
                                '                                                    </a>\n' +
                                '                                                </div>\n' +
                                '                                            </div>\n' +
                                '                                        </div>';
                            $(".kt_repeater_1").find(repeater).append(content);
                            if(value.salesType!=null){
                                $("select[name='salesInventories["+index+"].salesType'] option[value="+value.salesType.id+"]").attr("selected", "selected");
                            }
                        });


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

    function getImages(form, personId){
        var content = '';
        swal.fire({
            text: 'Proses davam edir...',
            allowOutsideClick: false,
            onOpen: function() {
                swal.showLoading();
                $.ajax({
                    url: '/common/api/person/document/'+personId,
                    type: 'GET',
                    dataType: 'json',
                    beforeSend: function() {
                        $(form).find("#image-content").html('');
                    },
                    success: function(data) {
                        console.log(data);
                        $.each(data, function( index, value ) {
                            if(value.fileContent!==""){
                                content+='<div class="col-6 text-center">' +
                                    '<img style="max-width: 90%; max-height: 240px" src="data:image/jpeg;base64, '+value.fileContent+'" />' +
                                    '</div>';
                            }
                        });
                        $(form).find("#image-content").html(content);
                        swal.close();
                    },
                    error: function() {
                        swal.fire({
                            title: "Xəta baş verdi!",
                            html: "Şəkil tapılmadı!",
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

    function getInventories(salesId, repeater){
        var content = '';
        swal.fire({
            text: 'Proses davam edir...',
            allowOutsideClick: false,
            onOpen: function() {
                swal.showLoading();
                $.ajax({
                    url: '/sale/api/service/inventory/'+salesId,
                    type: 'GET',
                    dataType: 'json',
                    beforeSend: function() {
                        $(".kt_repeater_1").find(repeater).html(content);
                    },
                    success: function(data) {
                        console.log(data);

                        $.each(data, function( index, value ) {
                            content='<div data-repeater-item="" class="form-group row align-items-center">\n' +
                                '                                            <div class="col-9">\n' +
                                '                                                <div class="kt-form__group--inline">\n' +
                                '                                                    <div class="kt-form__label">\n' +
                                '                                                        <label>İnventar:</label>\n' +
                                '                                                    </div>\n' +
                                '                                                    <div class="kt-form__control">\n' +
                                '                                                       <div class="row">\n' +
                                '                                                            <div class="col-7">\n' +
                                '                                                                <input type="text" attr="barcode" name="barcode" name="salesInventories['+index+'].inventory.barcode" class="form-control" placeholder="Barkodu daxil edin..." onchange="findInventory($(this))" value="'+value.inventory.barcode+'">\n' +
                                '                                                                <label attr="name" name="salesInventories['+index+'].inventory.name">'+value.inventory.name+'</label>\n' +
                                '                                                                <input type="hidden" attr="id" name="salesInventories['+index+'].inventory.id" class="form-control" value="'+value.inventory.id+'">\n' +
                                '                                                            </div>\n' +
                                '                                                            <div class="col-5">\n' +
                                '                                                                <select attr="type" name="salesInventories['+index+'].salesType" class="form-control" style="padding: 5px;">\n' +
                                '                                                                    <c:forEach var="t" items="${sales_types}" varStatus="loop">\n' +
                                '                                                                        <option value="${t.id}">${t.name}</option>\n' +
                                '                                                                    </c:forEach>\n' +
                                '                                                                </select>\n' +
                                '                                                            </div>\n' +
                                '                                                        </div>' +
                                '                                                    </div>\n' +
                                '                                                </div>\n' +
                                '                                                <div class="d-md-none kt-margin-b-10"></div>\n' +
                                '                                            </div>\n' +
                                '                                            <div class="col-3">\n' +
                                '                                                <div>\n' +
                                '                                                    <a href="javascript:;" data-repeater-delete="" class="btn-sm btn btn-label-danger btn-bold">\n' +
                                '                                                        <i class="la la-trash-o"></i>\n' +
                                '                                                        Sil\n' +
                                '                                                    </a>\n' +
                                '                                                </div>\n' +
                                '                                            </div>\n' +
                                '                                        </div>';
                            $(".kt_repeater_1").find(repeater).append(content);
                            $("select[name='salesInventories["+index+"].salesType'] option[value="+value.salesType.id+"]").attr("selected", "selected");
                        });


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

    function findInventory(element){
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
                        },
                        success: function(inventory) {
                            $(element).parent().find("input[attr='id']").val(inventory.id);
                            $(element).parent().find("label[attr='name']").text(inventory.name);
                            $('#form').find("input[name='payment.price']").val(round($('#form').find("input[name='payment.price']").val(), 0) + round(inventory.salePrice, 0));
                            $('#form').find("input[name='payment.price']").change();
                            swal.close();
                        },
                        error: function() {
                            swal.fire({
                                title: "Xəta baş verdi!",
                                html: "İnventar tapılmadı və ya "+$(element).val()+" barkodlu inventarın sayı 0 (sıfır)-dır!",
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

    function findInventory2(form, element){
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
                            /*$(form).find("input[name='payment.price']").val(round($(form).find("input[name='payment.price']").val(), 0) + round(inventory.salePrice, 0));
                            $(form).find("input[name='payment.price']").change();

                            console.log("2 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
                            console.log(round($(form).find("input[name='payment.price']").val(), 0));
                            console.log(round(inventory.salePrice, 0));*/
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

    function findInventory3(element, form, label){
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
                        },
                        success: function(inventory) {
                            $(label).text(inventory.name);
                            swal.close();
                        },
                        error: function() {
                            swal.fire({
                                title: "Xəta baş verdi!",
                                html: "İnventar tapılmadı və ya "+$(element).val()+" barkodlu inventarın sayı 0 (sıfır)-dır!",
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
    $('#group_table tbody').on('dblclick', 'tr', function () {
        swal.showLoading();
        if($(this).attr('cash')==="true"){
            location.href = '/collect/contact-history/'+ $(this).attr('data');
        } else {
            location.href = '/sale/schedule/'+ $(this).attr('data');
        }
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

    $("input[name='payment.price']").inputmask('decimal', {
        rightAlignNumerics: false
    });

    $("input[name='payment.gracePeriod']").inputmask('decimal', {
        rightAlignNumerics: false
    });

    $("input[name='payment.down']").inputmask('decimal', {
        rightAlignNumerics: false
    });

    $("input[name='payment.schedulePrice']").inputmask('decimal', {
        rightAlignNumerics: false
    });

    $("input[name='returnPrice']").inputmask('decimal', {
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

    var KTFormRepeater = function() {
        var demo1 = function() {
            $('.kt_repeater_1').repeater({
                initEmpty: false,

                defaultValues: {
                    'text-input': 'foo'
                },

                show: function () {
                    $(this).slideDown();
                    var elements = $($(this).parent()).find(".align-items-center");
                    var lastBefore = $(elements).eq(-2);
                    var barcode = $(lastBefore).find("input[attr='barcode']");
                    /*if(barcode.val().trim().length>0){
                        findInventory(barcode);
                    }*/
                },

                hide: function (deleteElement) {
                    swal.fire({
                        title: 'İnventarı ləğv etməyə əminsinizmi?',
                        type: 'info',
                        allowEnterKey: true,
                        showCancelButton: true,
                        buttonsStyling: false,
                        cancelButtonText: 'Xeyr, edilməsin!',
                        cancelButtonClass: 'btn btn-danger',
                        confirmButtonText: 'Bəli, edilsin!',
                        confirmButtonClass: 'btn btn-success',
                        reverseButtons: true,
                        allowOutsideClick: false,
                        footer: '<a href>Məlumatlar yenilənsinmi?</a>'
                    }).then(function(result){
                        if(result.value){
                            $(this).slideUp(deleteElement);
                        }
                    })
                }
            });
        };
        return {
            init: function() {
                demo1();
            }
        };
    }();

    jQuery(document).ready(function() {
        KTFormRepeater.init();
    });

    function returnOper(form, saleId, modal, title) {
        $(form).find("input[name='salesId']").val(saleId);
        $('#' + modal).find(".modal-title").html(title);
        $('#' + modal).modal('toggle');
    }

    function setTaxConfiguration(form, id, taxConfigurationId, modal, title) {
        $(form).find("input[name='id']").val(id);
        $(form).find("select[name='taxConfiguration.id'] option[value="+taxConfigurationId+"]").attr("selected", "selected");
        $('#' + modal).find(".modal-title").html(title);
        $('#' + modal).modal('toggle');
    }

    $( "#return-form" ).validate({
        rules: {
            returnPrice: {
                required: true,
                number: true,
                min: 0.0
            },
            reason: {
                required: true
            }
        },
        invalidHandler: function(event, validator) {
            KTUtil.scrollTop();
            swal.close();
        },
    });

    function sales(oper, form, dataId, modal, modal_title, person_id){
        swal.fire({
            text: 'Proses davam edir...',
            allowOutsideClick: false,
            onOpen: function() {
                swal.showLoading();
                $.ajax({
                    url: '/sale/api/sales/'+dataId,
                    type: 'GET',
                    dataType: 'text',
                    beforeSend: function() {

                    },
                    success: function(data) {
                        if(oper==="view"){
                            view(form, data, modal, modal_title)
                        } else if(oper==="edit"){
                            edit(form, data, modal, modal_title)
                        }
                        getInventories(dataId, $('#data-repeater-list'));
                        getImages(form, person_id);
                        swal.close();
                    },
                    error: function() {
                        swal.fire({
                            title: "Xəta",
                            html: "Xəta baş verdi!",
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

    function changeInventory(form, modal, modal_title, salesId, oldInventoryBarcode, oldInventoryName){
        $(form).find("input[name='salesId']").val(salesId);
        $(form).find("input[name='oldInventoryBarcode']").val(oldInventoryBarcode);
        $(form).find("#oldInventoryName").text(oldInventoryName);
        $('#' + modal).find(".modal-title").html(modal_title);
        $('#' + modal).modal('toggle');
    }

    $( "#tax-configuration-form" ).validate({
        rules: {
            taxConfiguration: {
                required: true
            }
        },
        invalidHandler: function(event, validator) {
            KTUtil.scrollTop();
            swal.close();
        },
    });

    $( "#payment-form" ).validate({
        rules: {
            'payment.price': {
                required: true,
                number: true
            },
            'guarantee.attr1': {
                required: true
            },
            saleDate: {
                required: true
            },
            'payment.gracePeriod': {
                required: true,
                number: true,
                pattern: /^(0|1)$/
            }
        },
        messages: {
            'payment.gracePeriod': "0 və ya 1 (~30 gün) ay ola bilər",
        },
        invalidHandler: function(event, validator) {
            KTUtil.scrollTop();
            swal.close();
        },
    });

    $( "#sales-employee-form" ).validate({
        rules: {
            'vanLeader.id': {
                required: true
            }
        },
        messages: {

        },
        invalidHandler: function(event, validator) {
            KTUtil.scrollTop();
            swal.close();
        },
    });

    $( "#change-inventory-form" ).validate({
        rules: {
            oldInventoryBarcode: {
                required: true
            },
            newInventoryBarcode: {
                required: true,
                notEqualTo: "#oldInventoryBarcode"
            }
        },
        messages: {

        },
        invalidHandler: function(event, validator) {
            KTUtil.scrollTop();
            swal.close();
        },
    });

    function selectLivingCity(form, element){
        $(form).find("select[name='customer.person.contact.livingCity.id'] option[value="+$(element).val()+"]").attr("selected", "selected");
    }
</script>