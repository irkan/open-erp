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

<link href="<c:url value="/assets/rate/jquery.raty.css" />" rel="stylesheet" type="text/css"/>
<script src="<c:url value="/assets/rate/jquery.raty.js" />" type="text/javascript"></script>
<div class="kt-container  kt-grid__item kt-grid__item--fluid">
    <div class="row">
        <div class="col-lg-12">
            <div class="kt-portlet kt-portlet--mobile">
                <div class="kt-portlet__body">
                    <c:choose>
                        <c:when test="${not empty list}">
                            <c:set var="transfer" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'transfer')}"/>
                            <c:set var="detail" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'detail')}"/>
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="datatable">
                                <thead>
                                <tr>
                                    <th>№</th>
                                    <th>Müştəri</th>
                                    <th>Satış</th>
                                    <th>Ödəniş tarixi</th>
                                    <th>Gecikir</th>
                                    <th>Qrafik</th>
                                    <th>Borc</th>
                                    <th>Satış</th>
                                    <th>Sonuncu qeyd</th>
                                    <th>Prioritetlik</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list}" varStatus="loop">
                                    <c:set var="now" value="<%=new Date().getTime()%>"/>
                                    <fmt:parseNumber var = "days" integerOnly = "true" type = "number" value = "${(now-t.scheduleDate.time)/86400000}" />
                                    <c:if test="${days le configuration_troubled_customer}">
                                        <tr data="<c:out value="${t.payment.id}" />">
                                            <td>${loop.index + 1}</td>
                                            <td>
                                                <a href="javascript:window.open('/crm/customer/<c:out value="${t.payment.sales.customer.id}"/>', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder"><c:out value="${t.payment.sales.customer.person.fullName}"/></a>
                                            </td>
                                            <td>
                                                <a href="javascript:window.open('/sale/sales/<c:out value="${t.payment.sales.id}" />', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder"><c:out value="${t.payment.sales.id}" /></a>
                                            </td>
                                            <td><fmt:formatDate value = "${t.scheduleDate}" pattern = "dd.MM.yyyy" /></td>
                                            <th>
                                                <c:set var="now" value="<%=new Date().getTime()%>"/>
                                                <fmt:parseNumber var = "days" integerOnly = "true" type = "number" value = "${(now-t.scheduleDate.time)/86400000}" />
                                                <a href="javascript:window.open('/sale/schedule/<c:out value="${t.payment.sales.payment.id}"/>', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder"><c:out value = "${days}" /> gün</a>
                                            </th>
                                            <th>Qrafik üzrə: <c:out value="${t.amount}" /> AZN<br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ödənilib: <c:out value="${t.payableAmount}" /> AZN
                                            </th>
                                            <th>
                                            <span class="kt-font-bold kt-font-danger">
                                                    <c:out value="${t.payableAmount-t.amount}" /> AZN
                                            </span>
                                            </th>
                                            <th>
                                                Qiymət: <c:out value="${t.payment.lastPrice}" /> AZN<br/>
                                                Ödənilib: <c:out value="${t.payment.down}" /> AZN<br/>
                                                Qalıq: <c:out value="${t.payment.lastPrice-t.payment.down}" /> AZN
                                            </th>
                                            <td>
                                                <c:if test="${t.payment.paymentRegulatorNotes.size()>0}">
                                                    <c:set var="prn" value="${t.payment.paymentRegulatorNotes.get(t.payment.paymentRegulatorNotes.size()-1)}"/>
                                                    <c:if test="${!prn.active and t.payment.paymentRegulatorNotes.size()>1}">
                                                        <c:set var="prn" value="${t.payment.paymentRegulatorNotes.get(t.payment.paymentRegulatorNotes.size()-2)}"/>
                                                    </c:if>
                                                    <c:if test="${prn.active}">
                                                        <span style="font-weight: bold"><c:out value="${prn.contactChannel.name}"/>,
                                                        <fmt:formatDate value = "${prn.createdDate}" pattern = "dd.MM.yyyy" />
                                                        <c:if test="${not empty prn.nextContactDate}">
                                                            <i class="la la-arrow-right"></i> <fmt:formatDate value = "${prn.nextContactDate}" pattern = "dd.MM.yyyy" />
                                                        </c:if>
                                                        </span>
                                                        <br/><c:out value="${prn.description}"/>
                                                    </c:if>
                                                </c:if>
                                            </td>
                                            <td>
                                                <div id="score-rating-<c:out value="${loop.index}" />" style="width: 130px;"></div>
                                                <script>
                                                    $(function(){
                                                        $('#score-rating-<c:out value="${loop.index}" />').raty({
                                                            score: <c:out value="${t.payment.priority}" />
                                                        });
                                                    });
                                                </script>
                                            </td>
                                            <td nowrap class="text-center">
                                                <c:if test="${transfer.status}">
                                                    <a href="javascript:transfer($('#form'), 'transfer-modal-operation', '<c:out value="${t.payment.sales.id}" />', '<c:out value="${t.amount-t.payableAmount}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${transfer.object.name}"/>">
                                                        <i class="<c:out value="${transfer.object.icon}"/>"></i>
                                                    </a>
                                                </c:if>
                                                <c:if test="${detail.status}">
                                                    <a href="/collect/payment-regulator-note/<c:out value="${t.payment.id}"/>" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="Qeydlər">
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

<div class="modal fade" id="transfer-modal-operation" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Hesab fakturaya göndər</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="form" method="post" action="/collect/troubled-customer/transfer" class="form-group">
                    <input type="hidden" name="sale" id="sale"/>
                    <div class="form-group">
                        <label for="price">Məbləğ</label>
                        <div class="input-group">
                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-usd"></i></span></div>
                            <input id="price" name="price" class="form-control" placeholder="Qiyməti daxil edin"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="description">Açıqlama</label>
                        <textarea id="description" name="description" class="form-control"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#form'));">İcra et</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<script>
    <c:if test="${detail.status}">
    $('#datatable tbody').on('dblclick', 'tr', function () {
        swal.showLoading();
        location.href = '/collect/payment-regulator-note/'+ $(this).attr('data');
        window.reload();
    });
    </c:if>
    function transfer(form, modal, saleId, price){
        try {
            $(form).find("input[name='sale']").val(saleId);
            $(form).find("input[name='price']").val(price);
            $('#' + modal).modal('toggle');
        } catch (e) {
            console.error(e);
        }
    }

    $("#datatable").DataTable({
        responsive: true,
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

    $( "#form" ).validate({
        rules: {
            price: {
                required: true,
                number: true
            }
        },
        invalidHandler: function(event, validator) {
            KTUtil.scrollTop();
            swal.close();
        },
    });
</script>





