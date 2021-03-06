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
                                <%--<c:out value="${list.totalElements>0?list.totalElements:0} sətr"/>--%>
                            </div>
                            <div class="col-6 text-center" style="letter-spacing: 10px;">
                                <c:out value="${filter.object.name}"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="filterContent" class="collapse show" aria-labelledby="headingOne" data-parent="#accordionFilter">
                    <div class="card-body">
                        <form:form modelAttribute="filter" id="filter" method="post" action="/sale/schedule/filter">
                            <div class="row">
                                <div class="col-md-11">
                                    <div class="row">
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="sales.id">Satış kodu</form:label>
                                                <form:input path="sales.id" cssClass="form-control" placeholder="#######" />
                                                <form:errors path="sales.id" cssClass="alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="sales.customer.id">Müştəri kodu</form:label>
                                                <form:input path="sales.customer.id" cssClass="form-control" placeholder="#######" />
                                                <form:errors path="sales.customer.id" cssClass="alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="scheduleDate">Tarix</form:label>
                                                <div class="input-group date" >
                                                    <form:input path="scheduleDate" autocomplete="off" date_="date_" cssClass="form-control datepicker-element" placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                <span class="input-group-text">
                                    <i class="la la-calendar"></i>
                                </span>
                                                    </div>
                                                </div>
                                                <form:errors path="scheduleDate" cssClass="control-label alert-danger" />
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
                        <c:when test="${not empty list.content}">
                            <c:set var="export" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'export')}"/>
                            <c:set var="transfer" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'transfer')}"/>
                            <c:set var="view1" value="${utl:checkOperation(sessionScope.user.userModuleOperations, 'sales', 'view')}"/>
                            <c:set var="view2" value="${utl:checkOperation(sessionScope.user.userModuleOperations, 'customer', 'view')}"/>
                            <c:set var="view3" value="${utl:checkOperation(sessionScope.user.userModuleOperations, 'contact-history', 'view')}"/>
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="datatable">
                                <thead>
                                <tr>
                                    <th>№</th>
                                    <th>Satış kodu</th>
                                    <th>Müştəri</th>
                                    <th>Müştəri ilə əlaqə</th>
                                    <th>Ödəniş tarixi</th>
                                    <th>Qrafik üzrə</th>
                                    <th>Ödənilib</th>
                                    <th>Qalıq</th>
                                    <th>Ümumi borc</th>
                                    <th>Gecikir</th>
                                    <th>Sonuncu qeyd</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="p" items="${list.content}" varStatus="loop1">
                                <c:forEach var="t" items="${p.schedules}" varStatus="loop">
                                    <c:set var="now" value="<%=new Date().getTime()%>"/>
                                    <fmt:parseNumber var = "days" integerOnly = "true" type = "number" value = "${(now-t.scheduleDate.time)/86400000}" />
                                    <c:if test="${(!p.singleContract and p.sales.payment.latency eq 0) or p.singleContract}">
                                        <tr>
                                            <th>${loop1.index+1}</th>
                                            <th style="<c:out value="${p.sales.payment.cash?'background-color: #e6ffe7 !important':'background-color: #ffeaf1 !important'}"/>"  data-sort="<c:out value="${p.sales.id}" />">
                                                <c:if test="${not empty p.sales.id}">
                                                    <a href="javascript:copyToClipboard2('<c:out value="${p.sales.id}" />', 'Satış kodu <b><c:out value="${p.sales.id}" /></b> kopyalandı')" class="kt-font-lg kt-font-bold kt-font-info kt-font-hover-danger pl-2 pr-2"><i class="la la-copy"></i></a>
                                                </c:if>
                                                <c:choose>
                                                    <c:when test="${view1.status}">
                                                        <c:choose>
                                                            <c:when test="${p.sales.service}">
                                                                <a href="javascript:window.open('/sale/service/<c:out value="${p.sales.id}" />', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder">Servis: <c:out value="${p.sales.id}" /></a>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <a href="javascript:window.open('/sale/sales/<c:out value="${p.sales.id}" />', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder">Satış: <c:out value="${p.sales.id}" /></a>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:choose>
                                                            <c:when test="${p.sales.service}">
                                                                Servis: <c:out value="${p.sales.id}" />
                                                            </c:when>
                                                            <c:otherwise>
                                                                Satış: <c:out value="${p.sales.id}" />
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:otherwise>
                                                </c:choose>
                                            </th>
                                            <th data-sort="<c:out value="${p.sales.customer.person.fullName}" />" data="<c:out value="${p.sales.customer.person.fullName}" />">
                                                <c:if test="${not empty p.sales.customer.id}">
                                                    <a href="javascript:copyToClipboard2('<c:out value="${p.sales.customer.id}" />', 'Müştəri kodu <b><c:out value="${p.sales.customer.id}" /></b> kopyalandı')" class="kt-font-lg kt-font-bold kt-font-info kt-font-hover-danger pl-2 pr-2"><i class="la la-copy"></i></a>
                                                </c:if>
                                                <c:choose>
                                                    <c:when test="${view2.status}">
                                                        <a href="javascript:window.open('/crm/customer/<c:out value="${p.sales.customer.id}"/>', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder"><c:out value="${p.sales.customer.person.fullName}"/></a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:out value="${p.sales.customer.person.fullName}"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </th>
                                            <td style="max-width: 280px">
                                                <div>
                                                    <a href="#" class="kt-link kt-font-bolder kt-font-danger"><c:out value="${p.sales.customer.person.contact.mobilePhone}"/></a>
                                                    <a href="#" class="kt-link kt-font-bolder kt-font-warning"><c:out value="${p.sales.customer.person.contact.homePhone}"/></a>
                                                    <a href="#" class="kt-link kt-font-bolder"><c:out value="${p.sales.customer.person.contact.relationalPhoneNumber1}"/></a>
                                                    <a href="#" class="kt-link kt-font-bolder"><c:out value="${p.sales.customer.person.contact.relationalPhoneNumber2}"/></a>
                                                    <a href="#" class="kt-link kt-font-bolder"><c:out value="${p.sales.customer.person.contact.relationalPhoneNumber3}"/></a>
                                                </div>
                                                <div>
                                                    <c:out value="${p.sales.customer.person.contact.city.name}"/>
                                                    <c:out value="${p.sales.customer.person.contact.address}"/>
                                                </div>
                                            </td>
                                            <td><fmt:formatDate value = "${t.scheduleDate}" pattern = "dd.MM.yyyy" /></td>
                                            <th data-sort="<c:out value="${t.amount}" />">
                                                <a href="javascript:window.open('/sale/schedule/<c:out value="${p.sales.id}"/>', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder"><c:out value="${t.amount}" /> AZN</a>
                                            </th>
                                            <th data-sort="<c:out value="${t.payableAmount gt 0?t.payableAmount:0}" />">
                                                <c:if test="${t.payableAmount>0}">
                                                    <c:out value="${t.payableAmount}" /> AZN
                                                </c:if>
                                            </th>
                                            <th data-sort="<c:out value="${t.payableAmount gt 0?(t.payableAmount-t.amount):0}" />">
                                                <c:if test="${t.payableAmount>0}">
                                                <span class="kt-font-bold kt-font-danger">
                                                    <c:out value="${t.payableAmount-t.amount}" /> AZN
                                                </span>
                                                </c:if>
                                            </th>
                                            <td data-sort="<c:out value="${p.sales.payment.unpaid}"/>">
                                                <c:out value="${p.sales.payment.unpaid}"/> AZN
                                            </td>
                                            <th data-sort="<c:out value="${(days>0 && t.payableAmount-t.amount<0)?days:0}"/>">
                                            <span class="kt-font-bold kt-font-info">
                                                <c:if test="${days>0 && t.payableAmount-t.amount<0}">
                                                    <c:out value = "${days}" /> gün
                                                </c:if>
                                            </span>
                                            </th>
                                            <td style="max-width: 270px">
                                                <c:if test="${view3.status}">
                                                    <c:if test="${p.sales.contactHistories.size()>0}">
                                                        <a href="javascript:window.open('/collect/contact-history/<c:out value="${p.sales.id}" />', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder">
                                                            <c:set var="ch" value="${p.sales.contactHistories.get(p.sales.contactHistories.size()-1)}"/>
                                                            <c:out value="${ch.user.username}"/>
                                                            <fmt:formatDate value = "${ch.createdDate}" pattern = "dd.MM.yyyy" /> -
                                                            <fmt:formatDate value = "${ch.nextContactDate}" pattern = "dd.MM.yyyy" /> -
                                                            <c:out value="${fn:substring(ch.description, 0, 94)}" />
                                                            <c:out value="${ch.description.length()>94?' . . . ':''}"/>
                                                        </a>
                                                    </c:if>
                                                </c:if>
                                                <c:if test="${!view3.status}">
                                                    <c:if test="${p.sales.contactHistories.size()>0}">
                                                        <c:set var="ch" value="${p.sales.contactHistories.get(p.sales.contactHistories.size()-1)}"/>
                                                        <c:out value="${ch.user.username}"/>
                                                        <fmt:formatDate value = "${ch.createdDate}" pattern = "dd.MM.yyyy" /> -
                                                        <fmt:formatDate value = "${ch.nextContactDate}" pattern = "dd.MM.yyyy" /> -
                                                        <c:out value="${fn:substring(ch.description, 0, 94)}" />
                                                        <c:out value="${ch.description.length()>94?' . . . ':''}"/>
                                                    </c:if>
                                                </c:if>
                                            </td>
                                            <td nowrap class="text-center">
                                                <c:if test="${transfer.status}">
                                                    <a href="javascript:transfer($('#form-transfer'), 'transfer-modal-operation', '<c:out value="${p.sales.id}" />', '<c:out value="${t.amount-t.payableAmount}" />', '<fmt:formatDate value = "${t.scheduleDate}" pattern = "dd.MM.yyyy" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${transfer.object.name}"/>">
                                                        <i class="<c:out value="${transfer.object.icon}"/>"></i>
                                                    </a>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
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
                <h5 class="modal-title">Hesab-faktura yarat</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="form-transfer" method="post" action="/sale/schedule/transfer" cssClass="form-group">
                    <form:hidden path="scheduleDate"/>
                    <div class="form-group">
                        <form:label path="sales.id">Satış kodu</form:label>
                        <form:input path="sales.id" cssClass="form-control" readonly="true"/>
                        <form:errors path="sales.id" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="form-group">
                        <form:label path="payableAmount">Ödəniləcək məbləğ</form:label>
                        <div class="input-group" >
                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-usd"></i></span></div>
                            <form:input path="payableAmount" cssClass="form-control" placeholder="Daxil edin"/>
                        </div>
                        <form:errors path="payableAmount" cssClass="alert-danger control-label"/>
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
                        <form:textarea path="description" cssClass="form-control" rows="4"/>
                        <form:errors path="description" cssClass="alert-danger control-label"/>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#form-transfer'));">Yadda saxla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<script>
    $(function(){
        $('#datatable').DataTable({
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
                    width: '25px',
                    className: 'dt-center',
                    orderable: false
                },
            ],
        });
    });

    function transfer(form, modal, salesId, payableAmount, invoiceDate){
        try {
            $(form).find("input[name='payableAmount']").val(payableAmount);
            $(form).find("input[name='sales.id']").val(salesId);
            $(form).find("input[name='invoiceDate']").val(invoiceDate);
            $('#' + modal).modal('toggle');
        } catch (e) {
            console.error(e);
        }
    }

    $( "#form-transfer" ).validate({
        rules: {
            "sales.id": {
                required: true
            },
            payableAmount: {
                required: true,
                number: true
            },
            description: {
                maxlength: 80
            }
        },
        invalidHandler: function(event, validator) {
            KTUtil.scrollTop();
            swal.close();
        },
    });

    $("input[name='amount']").inputmask('decimal', {
        rightAlignNumerics: false
    });

    $("input[name='payableAmount']").inputmask('decimal', {
        rightAlignNumerics: false
    });
</script>





