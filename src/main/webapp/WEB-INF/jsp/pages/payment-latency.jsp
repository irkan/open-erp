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
    <div class="row">
        <div class="col-lg-12">
            <div class="kt-portlet kt-portlet--mobile">
                <div class="kt-portlet__body">
                    <c:choose>
                        <c:when test="${not empty list}">
                            <c:set var="view" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'view')}"/>
                            <c:set var="detail" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'detail')}"/>
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="datatable">
                                <thead>
                                <tr>
                                    <th>Satış</th>
                                    <th>Müştəri</th>
                                    <th>Ödənilmişdir</th>
                                    <th>Ödənilməlidir</th>
                                    <th>Gecikir</th>
                                    <th>Satış tarixi</th>
                                    <th>Qrafik</th>
                                    <th>Qiymət</th>
                                    <th>Sonuncu qeyd</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list.content}" varStatus="loop">
                                    <c:if test="${t.payment.latency le configuration_troubled_customer}">
                                        <tr data="<c:out value="${t.id}" />">
                                            <td style="<c:out value="${t.payment.cash?'background-color: #e6ffe7 !important':'background-color: #ffeaf1 !important'}"/>">
                                                <a href="javascript:window.open('/sale/sales/<c:out value="${t.id}"/>', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder"><c:out value="${t.id}" /></a>
                                            </td>
                                            <th>
                                                <a href="javascript:window.open('/crm/customer/<c:out value="${t.payment.sales.customer.id}"/>', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder"><c:out value="${t.payment.sales.customer.id}"/>: <c:out value="${t.payment.sales.customer.person.fullName}"/></a>
                                            </th>
                                            <td>
                                                <span class="kt-font-bold kt-font-success"><c:out value="${t.payment.sumOfInvoice}" /></span>
                                                <span class="kt-font-bold kt-font-success font-italic font-size-10px">AZN</span>
                                            </td>
                                            <td>
                                                <a href="javascript:window.open('/sale/schedule/<c:out value="${t.id}"/>', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder">
                                                    <span class="kt-font-bold kt-font-danger"><c:out value="${t.payment.unpaid}" /></span>
                                                    <span class="kt-font-bold kt-font-danger font-italic font-size-10px">AZN</span>
                                                </a>
                                            </td>
                                            <td>
                                                <a href="javascript:window.open('/sale/schedule/<c:out value="${t.id}"/>', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder"><c:out value="${t.payment.latency}" /> gün</a>
                                            </td>
                                            <td><fmt:formatDate value = "${t.saleDate}" pattern = "dd.MM.yyyy" /></td>
                                            <td>
                                                <c:if test="${!t.payment.cash}">
                                                    <span class="kt-font-bold"><c:out value="${t.payment.schedule.name}" /> / <c:out value="${t.payment.schedulePrice}" /></span>
                                                    <span class="kt-font-bold font-italic font-size-10px">AZN</span>
                                                </c:if>
                                            </td>
                                            <th>
                                                <c:if test="${not empty t.payment.discount}">
                                                    Endirim: <c:out value="${t.payment.discount}" /><br/>
                                                </c:if>
                                                <c:if test="${not empty t.payment.description}">
                                                    Səbəbi: <c:out value="${t.payment.description}" /><br/>
                                                </c:if>
                                                <c:if test="${t.payment.down>0}">
                                                    İlkin ödəniş: <c:out value="${t.payment.down}" /><br/>
                                                </c:if>
                                                Son qiymət: <c:out value="${t.payment.lastPrice}" />

                                            </th>
                                            <td>
                                                <c:if test="${t.contactHistories.size()>0}">
                                                    <c:set var="ch" value="${t.contactHistories.get(t.contactHistories.size()-1)}"/>
                                                    <fmt:formatDate value = "${ch.createdDate}" pattern = "dd.MM.yyyy" /> -
                                                    <fmt:formatDate value = "${ch.nextContactDate}" pattern = "dd.MM.yyyy" /><br/>
                                                    <c:out value="${ch.description}"/>
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
                                            </td>
                                        </tr>
                                    </c:if>
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

    <c:if test="${view.status}">
    $('#datatable tbody').on('dblclick', 'tr', function () {
        swal.showLoading();
        location.href = '/collect/contact-history/'+ $(this).attr('data');
        window.reload();
    });
    </c:if>
</script>