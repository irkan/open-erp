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
<style>

    td { position: relative; }

    tr.strikeout td:before {
        content: " ";
        position: absolute;
        top: 46%;
        left: 0;
        border-bottom: 1px solid #e50f00;
        width: 100%;
    }

    tr.strikeout td:after {
        content: "\00B7";
        font-size: 1px;
    }

</style>
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
                        <form:form modelAttribute="filter" id="filter" method="post" action="/sale/invoice/filter">
                            <form:hidden path="organization" />
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
                                                <form:label path="sales">Satış kodu</form:label>
                                                <form:input path="sales" cssClass="form-control" placeholder="#######" />
                                                <form:errors path="sales" cssClass="alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="sales.customer">Müştəri kodu</form:label>
                                                <form:input path="sales.customer" cssClass="form-control" placeholder="#######" />
                                                <form:errors path="sales.customer" cssClass="alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="channelReferenceCode">Referans kod</form:label>
                                                <form:input path="channelReferenceCode" cssClass="form-control" placeholder="######"/>
                                                <form:errors path="channelReferenceCode" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="description">Açıqlama</form:label>
                                                <form:input path="description" cssClass="form-control" placeholder="Daxil edin"/>
                                                <form:errors path="description" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="invoiceDateFrom">Tarixdən</form:label>
                                                <div class="input-group date" >
                                                    <form:input path="invoiceDateFrom" autocomplete="off" date="date" cssClass="form-control datepicker-element" placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                    <span class="input-group-text">
                                        <i class="la la-calendar"></i>
                                    </span>
                                                    </div>
                                                </div>
                                                <form:errors path="invoiceDateFrom" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="invoiceDate">Tarixədək</form:label>
                                                <div class="input-group date" >
                                                    <form:input path="invoiceDate" autocomplete="off" date="date" cssClass="form-control datepicker-element" placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                    <span class="input-group-text">
                                        <i class="la la-calendar"></i>
                                    </span>
                                                    </div>
                                                </div>
                                                <form:errors path="invoiceDate" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="priceFrom">Qiymətdən</form:label>
                                                <form:input path="priceFrom" cssClass="form-control" placeholder="Qiyməti daxil edin"/>
                                                <form:errors path="priceFrom" cssClass="alert-danger control-label"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="price">Qiymətədək</form:label>
                                                <form:input path="price" cssClass="form-control" placeholder="Qiyməti daxil edin"/>
                                                <form:errors path="price" cssClass="alert-danger control-label"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="paymentChannel">Ödəniş kanalı</form:label>
                                                <form:select  path="paymentChannel.id" cssClass="custom-select form-control">
                                                    <form:option value=""></form:option>
                                                    <form:options items="${payment_channels}" itemLabel="name" itemValue="id" />
                                                </form:select>
                                                <form:errors path="paymentChannel" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2" style="padding-top: 30px;">
                                            <div class="form-group">
                                                <label class="kt-checkbox kt-checkbox--brand">
                                                    <form:checkbox path="approve"/> Təsdiq edilənlər
                                                    <span></span>
                                                </label>
                                            </div>
                                        </div>
                                        <c:if test="${delete.status}">
                                            <div class="col-md-2" style="padding-top: 30px;">
                                                <div class="form-group">
                                                    <label class="kt-checkbox kt-checkbox--brand">
                                                        <form:checkbox path="active"/> Aktual məlumat
                                                        <span></span>
                                                    </label>
                                                </div>
                                            </div>
                                        </c:if>
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
                            <c:set var="approve" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'approve')}"/>
                            <c:set var="consolidate" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'consolidate')}"/>
                            <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
                            <c:set var="credit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'credit')}"/>
                            <c:set var="export" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'export')}"/>
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="group_table">
                                <thead>
                                <tr>
                                    <th>HF nömrəsi</th>
                                    <th>Satış|Servis</th>
                                    <th>Status</th>
                                    <th>Müştəri</th>
                                    <th>Məbləğ</th>
                                    <th>Tarix</th>
                                    <th>Yığımçı|Servis</th>
                                    <th>Kanal</th>
                                    <th>Referans</th>
                                    <th>Avans</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list.content}" varStatus="loop">
                                    <tr data="<c:out value="${utl:toJson(t)}" />" class="<c:out value="${(t.price lt 0 and t.approve)?'strikeout':''}"/> ">
                                        <td><c:out value="${t.id}" /></td>
                                        <td style="<c:out value="${t.sales.payment.cash?'background-color: #e6ffe7 !important':'background-color: #ffeaf1 !important'}"/>">
                                            <c:choose>
                                                <c:when test="${t.sales.service}">
                                                    <a href="javascript:window.open('/sale/service/<c:out value="${t.sales.id}" />', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder">Servis: <c:out value="${t.sales.id}" /></a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a href="javascript:window.open('/sale/sales/<c:out value="${t.sales.id}" />', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder">Satış: <c:out value="${t.sales.id}" /></a>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${!t.approve}">
                                                    Təsdiq edilməyənlər
                                                </c:when>
                                                <c:otherwise>
                                                    Təsdiqlənənlər
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <a href="javascript:window.open('/crm/customer/<c:out value="${t.sales.customer.id}"/>', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder"><c:out value="${t.sales.customer.id}" />: <c:out value="${t.sales.customer.person.fullName}"/></a>
                                        </td>
                                        <td class="<c:out value="${t.creditable?'kt-bg-light-info':''}"/>">
                                            <span class="kt-font-bold"><c:out value="${t.price}" /></span>
                                            <span class="kt-font-bold font-italic font-size-10px">AZN</span>
                                        </td>
                                        <td><fmt:formatDate value = "${t.invoiceDate}" pattern = "dd.MM.yyyy" /></td>
                                        <td><c:out value="${t.collector.person.fullName}" /> <c:out value="${(not empty t.collector.person.fullName and not empty t.sales.servicer.person.fullName)?',':''}"/>
                                            <c:out value="${t.sales.servicer.person.fullName}"/></td>
                                        <td><c:out value="${t.paymentChannel.name}" /></td>
                                        <td><c:out value="${t.channelReferenceCode}" /></td>
                                        <td class="text-center">
                                            <c:if test="${t.advance}">
                                                <i class="flaticon2-check-mark kt-font-success"></i>
                                            </c:if>
                                        </td>
                                        <th nowrap class="text-center">
                                            <c:if test="${approve.status and !t.approve}">
                                                <a href="javascript:edit($('#form-approve'), '<c:out value="${utl:toJson(t)}" />', 'approve-modal', '<c:out value="${approve.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${approve.object.name}"/>">
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
                                                    <c:if test="${consolidate.status}">
                                                    <a href="javascript:consolidate($('#form-consolidate'), '<c:out value="${utl:toJson(t)}" />', 'consolidate-modal');" class="dropdown-item" title="<c:out value="${consolidate.object.name}"/>">
                                                        <i class="<c:out value="${consolidate.object.icon}"/>"></i> <c:out value="${consolidate.object.name}"/>
                                                    </a>
                                                    </c:if>
                                                    <c:if test="${edit.status and !t.approve}">
                                                    <a href="javascript:edit($('#form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${edit.object.name}" />');" class="dropdown-item" title="<c:out value="${edit.object.name}"/>">
                                                        <i class="<c:out value="${edit.object.icon}"/>"></i> <c:out value="${edit.object.name}"/>
                                                    </a>
                                                    </c:if>
                                                    <c:if test="${credit.status and t.creditable and t.approve}">
                                                    <a href="javascript:edit($('#credit-form'), '<c:out value="${utl:toJson(t)}" />', 'credit-modal-operation', '<c:out value="${credit.object.name}" />');" class="dropdown-item" title="<c:out value="${credit.object.name}"/>">
                                                        <i class="<c:out value="${credit.object.icon}"/>"></i> <c:out value="${credit.object.name}"/>
                                                    </a>
                                                    </c:if>
                                                    <c:if test="${delete.status and !t.approve}">
                                                    <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.description}" />');" class="dropdown-item" title="<c:out value="${delete.object.name}"/>">
                                                        <i class="<c:out value="${delete.object.icon}"/>"></i> <c:out value="${delete.object.name}"/>
                                                    </a>
                                                    </c:if>
                                                </div>
                                            </span>
                                            <c:if test="${export.status}">
                                            <a href="javascript:exportInvoice($('#form-export-invoice'), '<c:out value="${t.id}" />', 'modal-export-invoice');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="Hesab-fakturanın çapı">
                                                <i class="<c:out value="${export.object.icon}"/>"></i>
                                            </a>
                                            </c:if>
                                        </th>
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
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="form" method="post" action="/sale/invoice" cssClass="form-group">
                    <form:hidden path="id"/>
                    <form:hidden path="active"/>
                    <form:hidden path="organization"/>
                    <div class="form-group">
                        <form:label path="sales">Satış nömrəsi</form:label>
                        <div class="row">
                            <div class="col-9">
                                <div class="input-group">
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-search"></i></span></div>
                                    <form:input path="sales" class="form-control" placeholder="Daxil edin..."/>
                                </div>
                            </div>
                            <div class="col-3">
                                <button class="btn btn-primary" type="button" onclick="checkSales($('form').find('input[name=\'sales\']'))">Yoxla</button>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="price">Qiyməti</form:label>
                        <div class="input-group" >
                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-usd"></i></span></div>
                            <form:input path="price" cssClass="form-control" placeholder="Qiyməti daxil edin"/>
                        </div>
                        <form:errors path="price" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="form-group">
                        <form:label path="invoiceDate">Hesab-faktura tarixi</form:label>
                        <div class="input-group date" >
                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-calendar"></i></span></div>
                            <form:input path="invoiceDate" autocomplete="off" date="date" cssClass="form-control datepicker-element" placeholder="dd.MM.yyyy"/>
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
                <button type="button" class="btn btn-primary" onclick="submit($('#form'));">Yadda saxla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="approve-modal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Təsdiq</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="form-approve" method="post" action="/sale/invoice/approve" cssClass="form-group">
                    <form:hidden path="id"/>
                    <div class="row">
                        <div class="col-md-12 text-center">
                            <div class="form-group">
                                <label class="kt-checkbox kt-checkbox--brand">
                                    <input type="checkbox" name="advance"/> Avans hesablansınmı?
                                    <span></span>
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="description">Açıqlama</form:label>
                        <form:textarea path="description" cssClass="form-control"/>
                        <form:errors path="description" cssClass="alert-danger control-label"/>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#form-approve'));">Bəli, təsdiq edirəm!</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="credit-modal-operation" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Kredit</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="credit-form" method="post" action="/sale/invoice/credit" cssClass="form-group">
                    <form:hidden path="id"/>
                    <div class="form-group">
                        <form:label path="price">Kredit məbləği</form:label>
                        <div class="input-group" >
                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-usd"></i></span></div>
                            <form:input path="price" cssClass="form-control" placeholder="Qiyməti daxil edin" readonly="true"/>
                        </div>
                        <form:errors path="price" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="form-group">
                        <form:label path="description">Açıqlama</form:label>
                        <form:textarea path="description" cssClass="form-control"/>
                        <form:errors path="description" cssClass="alert-danger control-label"/>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#credit-form'));">Yadda saxla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="consolidate-modal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Yığımçıya təhkim edilmə</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="form-consolidate" method="post" action="/sale/invoice/consolidate" cssClass="form-group">
                    <form:hidden path="id"/>
                    <div class="form-group">
                        <form:label path="collector">Yığımçı</form:label>
                        <form:select  path="collector" cssClass="custom-select form-control select2-single" multiple="single">
                            <form:option value=""></form:option>
                            <c:forEach var="itemGroup" items="${employees}" varStatus="itemGroupIndex">
                                <optgroup label="${itemGroup.key}">
                                    <form:options items="${itemGroup.value}" itemLabel="person.fullName" itemValue="id"/>
                                </optgroup>
                            </c:forEach>
                        </form:select>
                        <form:errors path="collector" cssClass="control-label alert-danger"/>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#form-consolidate'));">Yadda saxla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modal-export-invoice" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Hesab faktura</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="form-export-invoice" method="post" action="/export/invoice/" class="form-group">
                    <div class="form-group">
                        <label for="data">Hesab faktura nömrəsi</label>
                        <input type="text" name="data" id="data" class="form-control"/>
                        <span class="form-text text-muted">Nümunə: 100001,100003-100008,100018</span>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submitTimeoutModal($('#form-export-invoice'), $('#modal-export-invoice'), 3000);">İcra et</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<script src="<c:url value="/assets/js/demo4/pages/crud/datatables/advanced/row-grouping.js" />" type="text/javascript"></script>

<script>
    function consolidate(form, data, modal){
        try {
            data = data.replace(/\&#034;/g, '"');
            var obj = jQuery.parseJSON(data);
            console.log(obj);
            $(form).find("#id").val(obj["id"]);
            if(obj["collector"]!=null){
                $("#collector option[value="+obj["collector"]["id"]+"]").attr("selected", "selected");
            }
            $('#' + modal).modal('toggle');
        } catch (e) {
            console.error(e);
        }
    }

    function exportInvoice(form, id, modal){
        try {
            $(form).find("#data").val(id);
            $('#' + modal).modal('toggle');
        } catch (e) {
            console.error(e);
        }
    }

    function approve(form, data, modal){
        try {
            data = data.replace(/\&#034;/g, '"');
            var obj = jQuery.parseJSON(data);
            console.log(obj);
            $(form).find("#id").val(obj["id"]);
            if(obj["collector"]!=null){
                $("#collector option[value="+obj["collector"]["id"]+"]").attr("selected", "selected");
            }
            $('#' + modal).modal('toggle');
        } catch (e) {
            console.error(e);
        }
    }

    $('#group_table tbody').on('dblclick', 'tr', function () {
        <c:if test="${edit.status}">
        edit($('#form'), $(this).attr('data'), 'modal-operation', '<c:out value="${edit.object.name}" />');
        </c:if>
        <c:if test="${!edit.status and view.status}">
        view($('#form'), $(this).attr('data'), 'modal-operation', '<c:out value="${view.object.name}" />');
        </c:if>
    });

    $( "#form" ).validate({
        rules: {
            price: {
                required: true,
                number: true,
                min: 1
            },
            sales: {
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

    $( "#form-consolidate" ).validate({
        rules: {
            id: {
                required: true,
                digits: true
            }
        },
        invalidHandler: function(event, validator) {
            KTUtil.scrollTop();
            swal.close();
        },
    });

    $( "#credit-form" ).validate({
        rules: {
            price: {
                required: true,
                number: true,
                min: 0
            }
        },
        invalidHandler: function(event, validator) {
            KTUtil.scrollTop();
            swal.close();
        },
    });

    $("input[name='price']").inputmask('decimal', {
        rightAlignNumerics: false
    });

    $("input[name='sales']").inputmask('decimal', {
        rightAlignNumerics: false
    });
</script>




