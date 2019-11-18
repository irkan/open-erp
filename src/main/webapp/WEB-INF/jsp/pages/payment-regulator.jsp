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
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="kt_table_1">
                                <thead>
                                <tr>
                                    <th>№</th>
                                    <th>Müştəri</th>
                                    <th>İnventar</th>
                                    <th>Ödəniş tarixi</th>
                                    <th>Gecikir</th>
                                    <th>Qrafik üzrə</th>
                                    <th>Ödənilib</th>
                                    <th>Qalıq</th>
                                    <th>Prioritetlik</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list}" varStatus="loop">
                                    <tr>
                                        <td>${loop.index + 1}</td>
                                        <td>
                                            <span style="font-size: 16px; font-weight: bold"><c:out value="${t.payment.sales.customer.person.fullName}"/></span>
                                            <c:if test="${not empty t.payment.sales.customer.person.contact.mobilePhone}">
                                                <br/><c:out value="${t.payment.sales.customer.person.contact.mobilePhone}"/>&nbsp;
                                            </c:if>
                                            <c:if test="${not empty t.payment.sales.customer.person.contact.homePhone}">
                                                <c:out value="${t.payment.sales.customer.person.contact.homePhone}"/>&nbsp;
                                            </c:if>
                                            <c:if test="${not empty t.payment.sales.customer.person.contact.address}">
                                                <br/><c:out value="${t.payment.sales.customer.person.contact.city.name}"/>,&nbsp;&nbsp;
                                                <c:out value="${t.payment.sales.customer.person.contact.address}"/>
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:out value="${t.payment.sales.action.inventory.name}" /><br/>
                                            <c:out value="${t.payment.sales.action.warehouse.name}" /><br/>
                                            <c:out value="${t.payment.sales.action.inventory.barcode}" />
                                        </td>
                                        <td><fmt:formatDate value = "${t.scheduleDate}" pattern = "dd.MM.yyyy" /></td>
                                        <th>
                                            <span class="kt-font-bold kt-font-info">
                                                <c:set var="now" value="<%=new Date().getTime()%>"/>
                                                <fmt:parseNumber var = "days" integerOnly = "true" type = "number" value = "${(now-t.scheduleDate.time)/86400000}" />
                                                <c:out value = "${days}" /> gün
                                            </span>
                                        </th>
                                        <th><c:out value="${t.amount}" /> AZN</th>
                                        <th><c:out value="${t.payableAmount}" /> AZN</th>
                                        <th>
                                            <span class="kt-font-bold kt-font-danger">
                                                    <c:out value="${t.payableAmount-t.amount}" /> AZN
                                            </span>
                                        </th>
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
                                            <c:set var="detail" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'detail')}"/>
                                            <c:choose>
                                                <c:when test="${detail.status}">
                                                    <a href="/collect/payment-regulator-detail/<c:out value="${t.payment.id}"/>" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${detail.object.name}"/>">
                                                        <i class="la <c:out value="${detail.object.icon}"/>"></i>
                                                    </a>
                                                </c:when>
                                            </c:choose>
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
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Başlıq</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#form'));">Yadda saxla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>




