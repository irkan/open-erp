<%--
  Created by IntelliJ IDEA.
  User: irkan.ahmadov
  Date: 20.09.2019
  Time: 1:22
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
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
                        <form:form modelAttribute="filter" id="filter" method="post" action="/collect/court/filter">
                            <form:hidden path="organization.id" />
                            <form:hidden path="service" />
                            <form:hidden path="court" />
                            <form:hidden path="execute" />
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
                                                        <form:label path="approveDateFrom">Satış tarixindən</form:label>
                                                        <div class="input-group date">
                                                            <form:input path="approveDateFrom" autocomplete="off"
                                                                        cssClass="form-control datepicker-element" date_="date_"
                                                                        placeholder="dd.MM.yyyy"/>
                                                            <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                            </div>
                                                        </div>
                                                        <form:errors path="approveDateFrom" cssClass="control-label alert-danger"/>
                                                    </div>
                                                </div>
                                                <div class="col-md-3">
                                                    <div class="form-group">
                                                        <form:label path="approveDate">Tarixədək</form:label>
                                                        <div class="input-group date">
                                                            <form:input path="approveDate" autocomplete="off"
                                                                        cssClass="form-control datepicker-element" date_="date_"
                                                                        placeholder="dd.MM.yyyy"/>
                                                            <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                            </div>
                                                        </div>
                                                        <form:errors path="approveDate" cssClass="control-label alert-danger"/>
                                                    </div>
                                                </div>
                                                <div class="col-md-3">
                                                    <div class="form-group">
                                                        <form:label path="payment.schedule.id">Ödəniş qrafiki</form:label>
                                                        <form:select  path="payment.schedule.id" cssClass="custom-select form-control">
                                                            <form:option value=""></form:option>
                                                            <form:options items="${payment_schedules}" itemLabel="name" itemValue="id" />
                                                        </form:select>
                                                        <form:errors path="payment.schedule.id" cssClass="control-label alert-danger"/>
                                                    </div>
                                                </div>
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
                                        <div class="col-md-4">
                                            <div class="row" style="padding-top: 30px;">
                                                <div class="col-md-3">
                                                    <div class="form-group">
                                                        <label class="kt-checkbox kt-checkbox--brand">
                                                            <form:checkbox path="payment.cash"/> Nağdlar
                                                            <span></span>
                                                        </label>
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label class="kt-checkbox kt-checkbox--brand">
                                                            <form:checkbox path="approve"/> Təsdiq edilməyənlər
                                                            <span></span>
                                                        </label>
                                                    </div>
                                                </div>
                                                <c:if test="${delete.status}">
                                                    <div class="col-md-3">
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
                            <c:set var="view1" value="${utl:checkOperation(sessionScope.user.userModuleOperations, 'invoice', 'view')}"/>
                            <c:set var="view2" value="${utl:checkOperation(sessionScope.user.userModuleOperations, 'customer', 'view')}"/>
                            <c:set var="view3" value="${utl:checkOperation(sessionScope.user.userModuleOperations, 'sales', 'view')}"/>
                            <c:set var="view4" value="${utl:checkOperation(sessionScope.user.userModuleOperations, 'schedule', 'view')}"/>
                            <c:set var="view5" value="${utl:checkOperation(sessionScope.user.userModuleOperations, 'contact-history', 'view')}"/>
                            <c:set var="detail" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'detail')}"/>
                            <c:set var="transfer" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'transfer')}"/>
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="datatable">
                                <thead>
                                <tr>
                                    <th>Kod</th>
                                    <th>Müştəri</th>
                                    <th>Ödəniş günü</th>
                                    <th>Ödənilmişdir</th>
                                    <th>Ödənilməlidir</th>
                                    <th>Gecikir</th>
                                    <th>Satış tarixi</th>
                                    <th>Sonuncu ödəniş</th>
                                    <th>Qrafik</th>
                                    <th>Qiymət</th>
                                    <th>Sonuncu qeyd</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list.content}" varStatus="loop">
                                    <tr data="<c:out value="${t.id}" />">
                                        <td data-sort="<c:out value="${t.id}" />" style="min-width: 120px; <c:out value="${t.payment.cash?'background-color: #e6ffe7 !important':'background-color: #ffeaf1 !important'}"/>">
                                            <c:if test="${not empty t.id}">
                                                <a href="javascript:copyToClipboard2('<c:out value="${t.id}" />', 'Satış kodu <b><c:out value="${t.id}" /></b> kopyalandı')" class="kt-font-lg kt-font-bold kt-font-info kt-font-hover-danger pl-2 pr-2"><i class="la la-copy"></i></a>
                                            </c:if>
                                            <c:choose>
                                                <c:when test="${view3.status}">
                                                    <c:choose>
                                                        <c:when test="${t.service}">
                                                            <a href="javascript:window.open('/sale/service/<c:out value="${t.id}" />', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder">Servis: <c:out value="${t.id}" /></a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <a href="javascript:window.open('/sale/sales/<c:out value="${t.id}" />', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder">Satış: <c:out value="${t.id}" /></a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:choose>
                                                        <c:when test="${t.service}">
                                                            Servis: <c:out value="${t.id}" />
                                                        </c:when>
                                                        <c:otherwise>
                                                            Satış: <c:out value="${t.id}" />
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <th data-sort="<c:out value="${t.payment.sales.customer.person.fullName}" />">
                                            <c:if test="${not empty t.payment.sales.customer.id}">
                                                <a href="javascript:copyToClipboard2('<c:out value="${t.payment.sales.customer.id}" />', 'Müştəri kodu <b><c:out value="${t.payment.sales.customer.id}" /></b> kopyalandı')" class="kt-font-lg kt-font-bold kt-font-info kt-font-hover-danger pl-2 pr-2"><i class="la la-copy"></i></a>
                                            </c:if>
                                            <c:choose>
                                                <c:when test="${view2.status}">
                                                    <a href="javascript:window.open('/crm/customer/<c:out value="${t.payment.sales.customer.id}"/>', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder"><c:out value="${t.payment.sales.customer.person.fullName}"/></a>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:out value="${t.payment.sales.customer.person.fullName}"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </th>
                                        <td data-sort="<c:out value="${t.payment.period.attr1}" />">
                                            <c:out value="${t.payment.period.name}" />
                                        </td>
                                        <td data-sort="<c:out value="${t.payment.sumOfInvoice}" />">
                                            <c:choose>
                                                <c:when test="${view1.status}">
                                                    <a href="javascript:window.open('/sale/invoice/<c:out value="${t.id}"/>', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder">
                                                        <span class="kt-font-bold kt-font-success"><c:out value="${t.payment.sumOfInvoice}" /></span>
                                                        <span class="kt-font-bold kt-font-success font-italic font-size-10px">AZN</span>
                                                    </a>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="kt-font-bold kt-font-success"><c:out value="${t.payment.sumOfInvoice}" /></span>
                                                    <span class="kt-font-bold kt-font-success font-italic font-size-10px">AZN</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td data-sort="<c:out value="${t.payment.unpaid}" />">
                                            <c:choose>
                                                <c:when test="${view4.status}">
                                                    <a href="javascript:window.open('/sale/schedule/<c:out value="${t.id}"/>', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder">
                                                        <span class="kt-font-bold kt-font-danger"><c:out value="${t.payment.unpaid}" /></span>
                                                        <span class="kt-font-bold kt-font-danger font-italic font-size-10px">AZN</span>
                                                    </a>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="kt-font-bold kt-font-danger"><c:out value="${t.payment.unpaid}" /></span>
                                                    <span class="kt-font-bold kt-font-danger font-italic font-size-10px">AZN</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td data-sort="<c:out value="${t.payment.latency}" />">
                                            <c:choose>
                                                <c:when test="${view4.status}">
                                                    <a href="javascript:window.open('/sale/schedule/<c:out value="${t.id}"/>', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder"><c:out value="${t.payment.latency}" /> gün</a>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:out value="${t.payment.latency}" /> gün
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td data-sort="<c:out value="${t.approveDate}" />"><fmt:formatDate value = "${t.approveDate}" pattern = "dd.MM.yyyy" /></td>
                                        <td><fmt:formatDate value = "${t.payment.lastPaid}" pattern = "dd.MM.yyyy" /></td>
                                        <td>
                                            <c:if test="${!t.payment.cash}">
                                                <span class="kt-font-bold"><c:out value="${t.payment.schedule.name}" /> / <c:out value="${t.payment.schedulePrice}" /></span>
                                                <span class="kt-font-bold font-italic font-size-10px">AZN</span>
                                            </c:if>
                                        </td>
                                        <th>
                                            Qiymət: <c:out value="${t.payment.price}" /><br/>
                                            Son qiymət: <c:out value="${t.payment.lastPrice}" /><br/>
                                            <c:if test="${not empty t.payment.discount}">
                                                Endirim: <c:out value="${t.payment.discount}" /><br/>
                                            </c:if>
                                            <c:if test="${t.payment.down>0}">
                                                İlkin ödəniş: <c:out value="${t.payment.down}" /><br/>
                                            </c:if>
                                            <c:if test="${!t.payment.cash}">
                                                Qrafik üzrə: <c:out value="${t.payment.schedulePrice}" /><br/>
                                            </c:if>
                                        </th>
                                        <td style="max-width: 300px">
                                            <c:if test="${view5.status}">
                                                <c:if test="${t.contactHistories.size()>0}">
                                                    <a href="javascript:window.open('/collect/contact-history/<c:out value="${t.id}" />', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder">
                                                        <c:set var="ch" value="${t.contactHistories.get(t.contactHistories.size()-1)}"/>
                                                        <c:out value="${ch.user.username}"/>
                                                        <fmt:formatDate value = "${ch.createdDate}" pattern = "dd.MM.yyyy" /> -
                                                        <fmt:formatDate value = "${ch.nextContactDate}" pattern = "dd.MM.yyyy" /> -
                                                        <c:out value="${fn:substring(ch.description, 0, 94)}" />
                                                        <c:out value="${ch.description.length()>94?' . . . ':''}"/>
                                                    </a>
                                                </c:if>
                                            </c:if>
                                            <c:if test="${!view5.status}">
                                                <c:if test="${t.contactHistories.size()>0}">
                                                    <c:set var="ch" value="${t.contactHistories.get(t.contactHistories.size()-1)}"/>
                                                    <c:out value="${ch.user.username}"/>
                                                    <fmt:formatDate value = "${ch.createdDate}" pattern = "dd.MM.yyyy" /> -
                                                    <fmt:formatDate value = "${ch.nextContactDate}" pattern = "dd.MM.yyyy" /> -
                                                    <c:out value="${fn:substring(ch.description, 0, 94)}" />
                                                    <c:out value="${ch.description.length()>94?' . . . ':''}"/>
                                                </c:if>
                                            </c:if>
                                        </td>
                                        <td nowrap class="text-center">
                                            <c:if test="${view.status}">
                                                <a href="/collect/contact-history/<c:out value="${t.id}"/>" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="Qeydlər">
                                                    <i class="la <c:out value="${view.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${detail.status}">
                                                <a href="/sale/schedule/<c:out value="${t.id}"/>" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${detail.object.name}"/>">
                                                    <i class="la <c:out value="${detail.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${transfer.status}">
                                                <a href="javascript:transfer($('#transfer-modal-operation'), $('#transfer-form'), '<c:out value="${t.id}" />', '<c:out value="${t.payment.unpaid}" />', 'Problemli müştəri: <c:out value="${t.payment.latency}" /> gün');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${transfer.object.name}"/>">
                                                    <i class="la <c:out value="${transfer.object.icon}"/>"></i>
                                                </a>
                                                <a href="javascript:transfer($('#send-execute-modal-operation'), $('#send-execute-form'), '<c:out value="${t.id}" />', '<c:out value="${t.payment.unpaid}" />', 'Problemli müştəri: <c:out value="${t.payment.latency}" /> gün');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${t.execute?'İcranın ləğvi':'İcraya yolla'}" />">
                                                    <i class="la <c:out value="${t.execute?'flaticon2-cross':'flaticon2-next'}" />"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${delete.status}">
                                                <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.id}" /> no satış və <c:out value="${t.payment.sales.customer.person.fullName}"/> <br/>müştərinin məhkəmədə statusu ləğv ediləcək!');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
                                                    <i class="<c:out value="${delete.object.icon}"/>"></i>
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

<div class="modal fade" id="transfer-modal-operation" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Hesab fakturaya yolla</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="transfer-form" method="post" action="/collect/${page}/transfer" cssClass="form-group">
                    <form:hidden path="id"/>
                    <form:hidden path="active"/>
                    <form:hidden path="organization.id"/>
                    <div class="form-group">
                        <form:label path="sales.id">Satış kodu</form:label>
                        <form:input path="sales.id" cssClass="form-control" readonly="true"/>
                        <form:errors path="sales.id" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="form-group">
                        <form:label path="price">Qiyməti</form:label>
                        <div class="input-group">
                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-usd"></i></span></div>
                            <form:input path="price" cssClass="form-control" placeholder="Qiyməti daxil edin"/>
                        </div>
                        <form:errors path="price" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="form-group">
                        <form:label path="invoiceDate">Hesab-faktura tarixi</form:label>
                        <div class="input-group date" >
                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-calendar"></i></span></div>
                            <form:input path="invoiceDate" autocomplete="off" date_="date_" cssClass="form-control datepicker-element" placeholder="dd.MM.yyyy"/>
                        </div>
                        <form:errors path="invoiceDate" cssClass="control-label alert-danger" />
                    </div>
                    <div class="form-group">
                        <form:label path="description">Açıqlama</form:label>
                        <form:textarea path="description" cssClass="form-control"/>
                        <form:errors path="description" cssClass="alert-danger control-label"/>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#transfer-form'));">Yadda saxla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="send-execute-modal-operation" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">İcraya yolla</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="send-execute-form" method="post" action="/collect/${page}/send-execute" cssClass="form-group">
                    <form:hidden path="id"/>
                    <form:hidden path="active"/>
                    <form:hidden path="organization.id"/>
                    <div class="form-group">
                        <form:label path="sales.id">Satış kodu</form:label>
                        <form:input path="sales.id" cssClass="form-control" readonly="true"/>
                        <form:errors path="sales.id" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="form-group">
                        <form:label path="description">Açıqlama</form:label>
                        <form:textarea path="description" cssClass="form-control"/>
                        <form:errors path="description" cssClass="alert-danger control-label"/>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#send-execute-form'));">İcraya yolla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<script>

    $("#datatable").DataTable({
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



    $( "#form" ).validate({
        rules: {
            price: {
                required: true,
                number: true,
                min: 1
            },
            "sales.id": {
                required: true
            },
            invoiceDate: {
                required: true
            }
        },
        invalidHandler: function(event, validator) {
            KTUtil.scrollTop();
            swal.close();
        },
    });

    <c:if test="${view.status}">
    $('#datatable tbody').on('dblclick', 'tr', function () {
        swal.showLoading();
        location.href = '/collect/contact-history/'+ $(this).attr('data');
        window.reload();
    });
    </c:if>

    $("input[name='price']").inputmask('decimal', {
        rightAlignNumerics: false
    });

    $("input[name='sales.id']").inputmask('decimal', {
        rightAlignNumerics: false
    });

    function transfer(modal, form, salesId, price, latency){
        $(form).find("input[name='sales.id']").val(salesId);
        $(form).find("input[name='price']").val(price);
        $(form).find("textarea[name='description']").val(latency);
        $(modal).modal('toggle');
    }
</script>