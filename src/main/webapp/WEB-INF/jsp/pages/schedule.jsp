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
                            <c:set var="transfer" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'transfer')}"/>
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="datatable">
                                <thead>
                                <tr class="bg-light">
                                    <th colspan="8" class="text-center">
                                        <span style="font-size: 16px; font-weight: bold"><c:out value="${list.sales.customer.person.fullName}"/></span><br/>
                                        <span style="font-size: 16px; font-weight: bold">Satış kodu: <c:out value="${list.sales.id}"/></span>
                                        <c:if test="${not empty list.sales.customer.person.contact.mobilePhone}">
                                            <br/><c:out value="${list.sales.customer.person.contact.mobilePhone}"/>&nbsp;
                                        </c:if>
                                        <c:if test="${not empty list.sales.customer.person.contact.homePhone}">
                                            <c:out value="${list.sales.customer.person.contact.homePhone}"/>&nbsp;
                                        </c:if>
                                        <c:if test="${not empty list.sales.customer.person.contact.address}">
                                            <br/><c:out value="${list.sales.customer.person.contact.city.name}"/>,&nbsp;&nbsp;
                                            <c:out value="${list.sales.customer.person.contact.address}"/>
                                        </c:if>
                                    </th>
                                </tr>
                                <tr>
                                    <th>№</th>
                                    <th>Satış kodu</th>
                                    <th>Ödəniş tarixi</th>
                                    <th>Qrafik üzrə</th>
                                    <th>Ödənilib</th>
                                    <th>Qalıq</th>
                                    <th>Gecikir</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list.schedules}" varStatus="loop">
                                    <c:set var="now" value="<%=new Date().getTime()%>"/>
                                    <fmt:parseNumber var = "days" integerOnly = "true" type = "number" value = "${(now-t.scheduleDate.time)/86400000}" />
                                    <tr>
                                        <th>${loop.index+1}</th>
                                        <th><c:out value="${list.sales.id}" /></th>
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

<%--<div class="modal fade" id="transfer-modal-operation" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Göndər</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="form-transfer" method="post" action="/sale/schedule/transfer" cssClass="form-group">
                    <form:hidden path="id"/>
                    <div class="form-group">
                        <form:label path="amount">Məbləğ</form:label>
                        <div class="input-group" >
                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-usd"></i></span></div>
                            <form:input path="amount" cssClass="form-control" placeholder="Daxil edin" readonly="true"/>
                        </div>
                        <form:errors path="amount" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="form-group">
                        <form:label path="payableAmount">Ödəniləcək məbləğ</form:label>
                        <div class="input-group" >
                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-usd"></i></span></div>
                            <form:input path="payableAmount" cssClass="form-control" placeholder="Daxil edin"/>
                        </div>
                        <form:errors path="payableAmount" cssClass="alert-danger control-label"/>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#form-transfer'));">Yadda saxla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>--%>

<script>
    $(function(){
        $('#datatable').DataTable({
            responsive: true,
            pageLength: 100,
            ordering: false
        });
    })

    function transfer(form, modal, id, amount, payableAmount){
        try {
            $(form).find("input[name='id']").val(id);
            $(form).find("input[name='amount']").val(amount);
            $(form).find("input[name='payableAmount']").val(payableAmount);
            $('#' + modal).modal('toggle');
        } catch (e) {
            console.error(e);
        }
    }

    $( "#form-transfer" ).validate({
        rules: {
            payableAmount: {
                required: true,
                number: true
            },
            amount: {
                required: true,
                number: true,
                min: 1
            },
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





