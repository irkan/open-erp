<%@ page import="java.util.Date" %><%--
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
                            <div class="row">
                                <div class="col-md-11">
                                    <div class="row">
                                        <div class="col-md-1">
                                            <div class="form-group">
                                                <form:label path="id">KOD</form:label>
                                                <form:input path="id" cssClass="form-control" placeholder="######"/>
                                                <form:errors path="id" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-1">
                                            <div class="form-group">
                                                <form:label path="payment">Ödəniş kodu</form:label>
                                                <form:input path="payment" cssClass="form-control" placeholder="######" />
                                                <form:errors path="payment" cssClass="alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="scheduleDateFrom">Satış tarixindən</form:label>
                                                <div class="input-group date">
                                                    <form:input path="scheduleDateFrom" autocomplete="off"
                                                                cssClass="form-control datepicker-element" date="date"
                                                                placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="scheduleDateFrom" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="scheduleDate">Tarixədək</form:label>
                                                <div class="input-group date">
                                                    <form:input path="scheduleDate" autocomplete="off"
                                                                cssClass="form-control datepicker-element" date="date"
                                                                placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="scheduleDate" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="amountFrom">Məbləğdən</form:label>
                                                <form:input path="amountFrom" cssClass="form-control" placeholder="Məbləği daxil edin"/>
                                                <form:errors path="amountFrom" cssClass="alert-danger control-label"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="amount">Məbləğədək</form:label>
                                                <form:input path="amount" cssClass="form-control" placeholder="Məbləği daxil edin"/>
                                                <form:errors path="amount" cssClass="alert-danger control-label"/>
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
                            <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
                            <c:set var="transfer" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'transfer')}"/>
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="datatable">
                                <thead>
                                <%--<tr class="bg-light">
                                    <th colspan="4" class="text-right">
                                        <span style="font-size: 16px; font-weight: bold"><c:out value="${object.customer.person.fullName}"/></span>
                                        <c:if test="${not empty object.customer.person.contact.mobilePhone}">
                                            <br/><c:out value="${object.customer.person.contact.mobilePhone}"/>&nbsp;
                                        </c:if>
                                        <c:if test="${not empty object.customer.person.contact.homePhone}">
                                            <c:out value="${object.customer.person.contact.homePhone}"/>&nbsp;
                                        </c:if>
                                        <c:if test="${not empty object.customer.person.contact.address}">
                                            <br/><c:out value="${object.customer.person.contact.city.name}"/>,&nbsp;&nbsp;
                                            <c:out value="${t.customer.person.contact.address}"/>
                                        </c:if>
                                        <c:if test="${not empty object.customer.person.contact.livingAddress}">
                                            <br/><c:out value="${object.customer.person.contact.livingCity.name}"/>,&nbsp;&nbsp;
                                            <c:out value="${object.customer.person.contact.livingAddress}"/>
                                        </c:if>
                                    </th>
                                    <th colspan="3">
                                        <c:forEach var="t" items="${list.content.salesInventories}" varStatus="loop">
                                            <c:out value="${t.inventory.name}" /><br/>
                                            <c:out value="${t.inventory.barcode}" /><br/>
                                            <div class="kt-separator kt-separator--dashed"></div>
                                        </c:forEach>
                                    </th>
                                </tr>--%>
                                <tr>
                                    <th>№</th>
                                    <th>Satış kodu</th>
                                    <th>Ödəniş tarixi</th>
                                    <th>Qrafik üzrə</th>
                                    <th>Ödənilib</th>
                                    <th>Qalıq</th>
                                    <th>Gecikir</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list.content}" varStatus="loop">
                                    <c:set var="now" value="<%=new Date().getTime()%>"/>
                                    <fmt:parseNumber var = "days" integerOnly = "true" type = "number" value = "${(now-t.scheduleDate.time)/86400000}" />
                                    <tr>
                                        <th>${loop.index+1}</th>
                                        <th><c:out value="${t.payment.sales.id}" /></th>
                                        <td><fmt:formatDate value = "${t.scheduleDate}" pattern = "dd.MM.yyyy" /></td>
                                        <th><c:out value="${t.amount}" /> AZN</th>
                                        <th>
                                            <c:if test="${t.payableAmount>0}">
                                                <c:out value="${t.payableAmount}" /> AZN
                                            </c:if>
                                        </th>
                                        <th>
                                            <c:if test="${t.payableAmount>0}">
                                                <span class="kt-font-bold kt-font-danger">
                                                    <c:out value="${t.payableAmount-t.amount}" /> AZN
                                                </span>
                                            </c:if>
                                        </th>
                                        <th>
                                            <span class="kt-font-bold kt-font-info">
                                                <c:if test="${days>0 && t.payableAmount-t.amount<0}">
                                                    <c:out value = "${days}" /> gün
                                                </c:if>
                                            </span>
                                        </th>
                                        <td nowrap class="text-center">
                                            <c:if test="${transfer.status}">
                                                <a href="javascript:transfer($('#form'), 'transfer-modal-operation', '<c:out value="${t.payment.sales.id}" />', '<c:out value="${t.amount-t.payableAmount}" />')" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${transfer.object.name}"/>">
                                                    <i class="<c:out value="${transfer.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                            <span class="dropdown">
                                                <a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" data-toggle="dropdown" aria-expanded="true">
                                                  <i class="la la-ellipsis-h"></i>
                                                </a>
                                                <div class="dropdown-menu dropdown-menu-right">
                                                    <c:if test="${edit.status}">
                                                        <a href="javascript:edit($('#form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${edit.object.name}" />');" class="dropdown-item" title="<c:out value="${edit.object.name}"/>">
                                                            <i class="<c:out value="${edit.object.icon}"/>"></i> <c:out value="${edit.object.name}"/>
                                                        </a>
                                                    </c:if>
                                                    <c:if test="${delete.status}">
                                                        <a href="javascript:deleteData('<c:out value="${t.id}" />', '<fmt:formatDate value = "${t.scheduleDate}" pattern = "dd.MM.yyyy" /> <br/> <c:out value="${t.payableAmount}" />');" class="dropdown-item" title="<c:out value="${delete.object.name}"/>">
                                                            <i class="<c:out value="${delete.object.icon}"/>"></i> <c:out value="${delete.object.name}"/>
                                                        </a>
                                                    </c:if>
                                                </div>
                                            </span>
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
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Yeni sorğu yarat</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="form" method="post" action="/sale/schedule" cssClass="form-group">
                    <form:hidden path="id"/>
                    <form:hidden path="organization"/>
                    <form:hidden path="active"/>
                    <div class="form-group">
                        <form:label path="scheduleDate">Tarixədək</form:label>
                        <div class="input-group date">
                            <form:input path="scheduleDate" autocomplete="off"
                                        cssClass="form-control datepicker-element" date="date"
                                        placeholder="dd.MM.yyyy"/>
                            <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                            </div>
                        </div>
                        <form:errors path="scheduleDate" cssClass="control-label alert-danger"/>
                    </div>
                    <div class="form-group">
                        <form:label path="amount">Məbləğ</form:label>
                        <form:input path="amount" cssClass="form-control" placeholder="Daxil edin" readonly="true"/>
                        <form:errors path="amount" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="form-group">
                        <form:label path="payableAmount">Ödənilmiş məbləğ</form:label>
                        <form:input path="payableAmount" cssClass="form-control" placeholder="Daxil edin"/>
                        <form:errors path="payableAmount" cssClass="alert-danger control-label"/>
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

<div class="modal fade" id="transfer-modal-operation" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Göndər</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="form" method="post" action="/sale/sales-detail/transfer" class="form-group">
                    <input type="hidden" name="sale" id="sale"/>
                    <div class="form-group">
                        <label for="transfer">Haraya?</label>
                        <select class="custom-select form-control" name="transfer" id="transfer">
                            <option value="1">Hesab-fakturaya</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="price">Qiyməti</label>
                        <div class="input-group" >
                            <input id="price" name="price" class="form-control" placeholder="Qiyməti daxil edin"/>
                            <div class="input-group-append">
                                <span class="input-group-text">
                                    <i class="la la-usd"></i>
                                </span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="description">Açıqlama</label>
                        <textarea id="description" name="description" class="form-control"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#form'));">Yadda saxla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<script>
    $(function(){
        $('#datatable').DataTable({
            responsive: true,
            pageLength: 100,
            ordering: false
        });
    })

    function transfer(form, modal, saleId, price){
        try {
            $(form).find("input[name='sale']").val(saleId);
            $(form).find("input[name='price']").val(price);
            $('#' + modal).modal('toggle');
        } catch (e) {
            console.error(e);
        }
    }
</script>





