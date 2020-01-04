<%--
  Created by IntelliJ IDEA.
  User: irkan.ahmadov
  Date: 01.09.2019
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
    <tr>
        <th>№</th>
        <th>ID</th>
        <th>Flial</th>
        <th>Hərəkət</th>
        <th>Təchizatçı</th>
        <th>İnventar</th>
        <th>Barkod</th>
        <th>Miqdar</th>
        <th>Tarix</th>
        <th>Təhkim edilib</th>
        <th>Əməliyyat</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="t" items="${list}" varStatus="loop">
        <tr>
            <td>${loop.index + 1}</td>
            <td><c:out value="${t.id}" /></td>
            <td><c:out value="${t.organization.name}" /></td>
            <td><c:out value="${t.action.name}" /></td>
            <td><c:out value="${t.supplier.name}" /></td>
            <td><c:out value="${t.inventory.name}" /></td>
            <td><c:out value="${t.inventory.barcode}" /></td>
            <td><c:out value="${t.amount}" /></td>
            <td><fmt:formatDate value = "${t.createdDate}" pattern = "dd.MM.yyyy" /></td>
            <td><c:out value="${t.employee.person.fullName}" /></td>
            <td nowrap class="text-center">
                <c:set var="return1" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'return')}"/>
                <c:choose>
                    <c:when test="${return1.status and t.action.attr1 eq 'consolidate'}">
                        <c:if test="${t.amount gt 0}">
                            <a href="javascript:returnOperation($('#form-return'), '<c:out value="${utl:toJson(t)}" />', 'return-modal');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${return1.object.name}"/>">
                                <i class="<c:out value="${return1.object.icon}"/>"></i>
                            </a>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${t.approve}">
                                <span class="kt-font-bold kt-font-success">Qəbul edilib</span>
                            </c:when>
                            <c:otherwise>
                                <span class="kt-spinner kt-spinner--md kt-spinner--danger" style="margin-right: 25px;"></span>
                                <span class="kt-font-bold kt-font-danger">Qəbul edilməyib</span>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
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



<div class="modal fade" id="return-modal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Anbara qaytarılma</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="form-return" method="post" action="/warehouse/consolidate/return" cssClass="form-group">
                    <form:hidden path="id"/>
                    <div class="row">
                        <div class="col-sm-12 text-center">
                            <label id="inventory_name" style="font-size: 16px; font-weight: bold;"></label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 text-right">
                            <label>Barkod</label>
                        </div>
                        <div class="col-sm-6">
                            <label id="barcode_label"></label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 text-right">
                            <label>Anbar</label>
                        </div>
                        <div class="col-sm-6">
                            <label id="warehouse_label"></label>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="amount">Say</form:label>
                        <form:input path="amount" cssClass="form-control" placeholder="Sayı daxil edin"  />
                        <form:errors path="amount" cssClass="alert alert-danger"/>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#form-return'));">Yadda saxla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<script>
    function returnOperation(form, data, modal){
        try {
            data = data.replace(/\&#034;/g, '"');
            var obj = jQuery.parseJSON(data);
            console.log(obj);
            $(form).find("input[name='amount']").val(obj["amount"]);
            $(form).find("#id").val(obj["id"]);
            $(form).find("#inventory_name").text(obj["inventory"]["name"]);
            $(form).find("#barcode_label").text(obj["inventory"]["barcode"]);
            $(form).find("#warehouse_label").text(obj["warehouse"]["name"]);
            $('#' + modal).modal('toggle');
        } catch (e) {
            console.error(e);
        }
    }
</script>


