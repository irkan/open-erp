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
                        <c:when test="${not empty list}">
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="kt_table_1">
                                <thead>
                                <tr class="bg-light">
                                    <th colspan="4" class="text-right">
                                        <c:forEach var="t" items="${list}" varStatus="loop">
                                            <c:if test="${loop.index==0}">
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
                                            </c:if>
                                        </c:forEach>
                                    </th>
                                    <th colspan="3">
                                        <c:forEach var="t" items="${list}" varStatus="loop">
                                            <c:if test="${loop.index==0}">
                                                <c:out value="${t.payment.sales.action.inventory.name}" /><br/>
                                                <c:out value="${t.payment.sales.action.warehouse.name}" /><br/>
                                                <c:out value="${t.payment.sales.action.inventory.barcode}" />
                                            </c:if>
                                        </c:forEach>
                                    </th>
                                </tr>
                                <tr>
                                    <th>№</th>
                                    <th>ID</th>
                                    <th>Əlaqə kanalı</th>
                                    <th>Əlaqə tarixi</th>
                                    <th>Növbəti əlaqə tarixi</th>
                                    <th>Açıqlama</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list}" varStatus="loop">
                                    <tr>
                                        <td>${loop.index + 1}</td>
                                        <td><c:out value="${t.id}"/></td>
                                        <td><c:out value="${t.contactChannel.name}"/></td>
                                        <td><fmt:formatDate value = "${t.createdDate}" pattern = "dd.MM.yyyy" /></td>
                                        <td><fmt:formatDate value = "${t.nextContactDate}" pattern = "dd.MM.yyyy" /></td>
                                        <td><c:out value="${t.description}"/></td>
                                        <td nowrap class="text-center">
                                            <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
                                            <c:choose>
                                                <c:when test="${edit.status}">
                                                    <a href="javascript:console.log($(this).parents('tr').html());edit($('#form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${edit.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${edit.object.name}"/>">
                                                        <i class="<c:out value="${edit.object.icon}"/>"></i>
                                                    </a>
                                                </c:when>
                                            </c:choose>
                                            <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
                                            <c:choose>
                                                <c:when test="${delete.status}">
                                                    <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.description}"/>');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
                                                        <i class="<c:out value="${delete.object.icon}"/>"></i>
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
                <form:form modelAttribute="form" id="form" method="post" action="/collect/payment-regulator-note" cssClass="form-group">
                    <form:hidden path="id"/>
                    <input type="hidden" name="payment" id="payment" value="<c:out value="${payment_id}"/>">
                    <div class="form-group">
                        <form:radiobuttons items="${contact_channels}" path="contactChannel" itemLabel="name" itemValue="id"/>
                        <form:errors path="contactChannel" cssClass="control-label alert-danger" />
                    </div>
                    <div class="form-group">
                        <form:label path="nextContactDate">Növbəti əlaqə taixi</form:label>
                        <div class="input-group date" >
                            <form:input path="nextContactDate" autocomplete="off" date="date" cssClass="form-control datepicker-element" placeholder="dd.MM.yyyy"/>
                            <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                            </div>
                        </div>
                        <form:errors path="nextContactDate" cssClass="control-label alert-danger" />
                    </div>
                    <div class="form-group">
                        <form:label path="description">Açıqlama</form:label>
                        <form:textarea path="description" cssClass="form-control"/>
                        <form:errors path="description" cssClass="control-label alert-danger" />
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





