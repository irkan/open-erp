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
    <div class="row">
        <div class="col-lg-12">
            <div class="kt-portlet kt-portlet--mobile">
                <div class="kt-portlet__body">
                    <c:choose>
                        <c:when test="${not empty object}">
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="datatable">
                                <thead>
                                <tr class="bg-light">
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
                                        <c:forEach var="t" items="${object.salesInventories}" varStatus="loop">
                                            <c:out value="${t.inventory.name}" /><br/>
                                            <c:out value="${t.inventory.barcode}" /><br/>
                                            <div class="kt-separator kt-separator--dashed"></div>
                                        </c:forEach>
                                    </th>
                                </tr>
                                <tr>
                                    <th>№</th>
                                    <th>Ödəniş tarixi</th>
                                    <th>Gecikir</th>
                                    <th>Qrafik üzrə</th>
                                    <th>Ödənilib</th>
                                    <th>Qalıq</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${object.payment.schedules}" varStatus="loop">
                                    <tr>
                                        <th>${loop.index+1}</th>
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

<script>
    $(function(){
        $('#datatable').DataTable({
            responsive: true,
            pageLength: 100,
            ordering: false
        });
    })
</script>





