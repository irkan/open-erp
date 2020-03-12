<%--
  Created by IntelliJ IDEA.
  User: irkan.ahmadov
  Date: 01.09.2019
  Time: 1:22Təhkim etmə

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
                        <form:form modelAttribute="filter" id="filter" method="post" action="/warehouse/action/filter">
                            <form:hidden path="organization" />
                            <form:hidden path="inventory.id" />
                            <form:hidden path="inventory.active" htmlEscape="true" value="1" />
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
                                                <form:label path="actionDateFrom">Tarixdən</form:label>
                                                <div class="input-group date" >
                                                    <form:input path="actionDateFrom" autocomplete="off" date_="date_" cssClass="form-control datetimepicker-element" placeholder="dd.MM.yyyy HH:mm"/>
                                                    <div class="input-group-append">
                                    <span class="input-group-text">
                                        <i class="la la-calendar"></i>
                                    </span>
                                                    </div>
                                                </div>
                                                <form:errors path="actionDateFrom" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="actionDate">Tarixədək</form:label>
                                                <div class="input-group date" >
                                                    <form:input path="actionDate" autocomplete="off" date_="date_" cssClass="form-control datetimepicker-element" placeholder="dd.MM.yyyy HH:mm"/>
                                                    <div class="input-group-append">
                                    <span class="input-group-text">
                                        <i class="la la-calendar"></i>
                                    </span>
                                                    </div>
                                                </div>
                                                <form:errors path="actionDate" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="amountFrom">Saydan</form:label>
                                                <form:input path="amountFrom" cssClass="form-control" placeholder="Sayı daxil edin"/>
                                                <form:errors path="amountFrom" cssClass="alert-danger control-label"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="amount">Sayadək</form:label>
                                                <form:input path="amount" cssClass="form-control" placeholder="Qiyməti daxil edin"/>
                                                <form:errors path="amount" cssClass="alert-danger control-label"/>
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
                                        <div class="col-md-2" style="padding-top: 30px;">
                                            <div class="form-group">
                                                <label class="kt-checkbox kt-checkbox--brand">
                                                    <form:checkbox path="old"/> İşlənmiş
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
        <c:set var="return1" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'return')}"/>
        <c:set var="view" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'view')}"/>
        <c:set var="approve" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'approve')}"/>
        <c:set var="transfer" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'transfer')}"/>
        <c:set var="consolidate" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'consolidate')}"/>
        <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
<table class="table table-striped- table-bordered table-hover table-checkable" id="datatable">
    <thead>
    <tr>
        <th>№</th>
        <th>ID</th>
        <th>Hərəkət</th>
        <th>Flial#1</th>
        <th>Flial#2</th>
        <th>Təchizatçı</th>
        <th>İnventar</th>
        <th>Miqdar</th>
        <th>Tarix</th>
        <th>Təhkim edilib</th>
        <th>Vəziyyət</th>
        <th>Əməliyyat</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="t" items="${list.content}" varStatus="loop">

        <tr data="<c:out value="${utl:toJson(t)}" />"
            <c:if test="${t.action.attr1=='send' or t.action.attr1=='sell'}">
                style="background-color: #ffeaf1 !important"
            </c:if>
            <c:if test="${t.action.attr1=='accept' or t.action.attr1=='buy'}">
                style="background-color: #e6ffe7 !important"
            </c:if>
        >
            <td>${loop.index + 1}</td>
            <td><c:out value="${t.id}" /></td>
            <td><c:out value="${t.action.name}" /></td>
            <td><c:out value="${t.organization.name}" /></td>
            <td><c:out value="${t.fromOrganization.name}" /></td>
            <td><c:out value="${t.supplier.name}" /></td>
            <td><c:out value="${t.inventory.name}" /></td>
            <td><c:out value="${t.amount}" /></td>
            <td><fmt:formatDate value = "${t.createdDate}" pattern = "dd.MM.yyyy" /></td>
            <td><c:out value="${t.employee.person.fullName}" /></td>
            <td>
                <c:choose>
                    <c:when test="${t.old}">
                        <span class="kt-badge kt-badge--danger kt-badge--inline kt-badge--pill">İşlənmiş</span>
                    </c:when>
                    <c:otherwise>
                        <span class="kt-badge kt-badge--success kt-badge--inline kt-badge--pill">Yeni</span>
                    </c:otherwise>
                </c:choose>
            </td>
            <td nowrap class="text-center">
                <c:if test="${!(t.action.attr1 eq 'sell') and !(t.action.attr1 eq 'cancellation') and !(t.action.attr1 eq 'send' and t.approve)}">
                    <c:if test="${return1.status and t.action.attr1 eq 'consolidate'}">
                        <a href="javascript:returnOperation($('#form-return'), '<c:out value="${utl:toJson(t)}" />', 'return-modal');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${return1.object.name}"/>">
                            <i class="<c:out value="${return1.object.icon}"/>"></i>
                        </a>
                    </c:if>
                    <c:if test="${view.status}">
                        <a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${view.object.name}"/>">
                            <i class="la <c:out value="${view.object.icon}"/>"></i>
                        </a>
                    </c:if>
                    <c:if test="${approve.status and !t.approve}">
                        <a href="javascript:approve($('#transfer-approve-form'), $('#transfer-approve-modal'), '<c:out value="${t.id}" />', '<c:out value="${t.inventory.id}" />', '<c:out value="${t.inventory.name}" />', '<c:out value="${t.inventory.barcode}" />', '<c:out value="${t.organization.name}" />', '<c:out value="${t.action.name}" />', '<c:out value="${t.supplier.name}" />', '<c:out value="${t.amount}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${approve.object.name}"/>">
                            <i class="<c:out value="${approve.object.icon}"/>"></i>
                        </a>
                    </c:if>
                    <c:if test="${transfer.status and t.approve and !(t.action.attr1 eq 'consolidate') and t.amount gt 0}">
                        <a href="javascript:transfer($('#form'), '<c:out value="${utl:toJson(t)}" />', 'transfer-modal-operation');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${transfer.object.name}"/>">
                            <i class="<c:out value="${transfer.object.icon}"/>"></i>
                        </a>
                    </c:if>
                    <c:if test="${consolidate.status and t.approve and !(t.action.attr1 eq 'consolidate') and t.amount gt 0}">
                        <a href="javascript:consolidate($('#form-consolidate'), '<c:out value="${utl:toJson(t)}" />', 'consolidate-modal');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${consolidate.object.name}"/>">
                            <i class="<c:out value="${consolidate.object.icon}"/>"></i>
                        </a>
                    </c:if>
                </c:if>
                <c:if test="${delete.status}">
                    <c:if test="${t.approve and t.action.attr1 eq 'buy' and t.amount gt 0}">
                        <a href="javascript:returnOperation($('#form-cancellation'), '<c:out value="${utl:toJson(t)}" />', 'cancellation-modal');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="Ləğv edilmə">
                            <i class="<c:out value="${delete.object.icon}"/>"></i>
                        </a>
                    </c:if>
                    <c:if test="${t.approve and t.action.attr1 eq 'cancellation'}">
                        <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.action.name}" /> / <c:out value="${t.organization.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
                            <i class="<c:out value="${delete.object.icon}"/>"></i>
                        </a>
                    </c:if>
                </c:if>
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
                <form:form modelAttribute="form" id="form" method="post" action="/warehouse/action/transfer" cssClass="form-group">
                    <input type="hidden" name="id"/>
                    <input type="hidden" name="inventory"/>
                    <input type="hidden" name="action"/>
                    <input type="hidden" name="supplier"/>
                    <input type="hidden" name="fromOrganization"/>
                    <div class="form-group">
                        <label for="from">Haradan?</label>
                        <input name="from" id="from" class="form-control" readonly/>
                    </div>
                    <div class="form-group">
                        <form:label path="organization">Haraya?</form:label>
                        <form:select  path="organization" cssClass="custom-select form-control">
                            <form:option value=""></form:option>
                            <form:options items="${organizations}" itemLabel="name" itemValue="id" />
                        </form:select>
                        <form:errors path="organization" cssClass="alert-danger"/>
                    </div>
                    <div class="form-group">
                        <form:label path="amount">Say</form:label>
                        <div class="input-group" >
                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-calculator"></i></span></div>
                            <form:input path="amount" cssClass="form-control" placeholder="Say daxil edin"/>
                        </div>
                        <form:errors path="amount" cssClass="alert-danger"/>
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

<div class="modal fade" id="transfer-approve-modal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="transfer-approve-form" method="post" action="/warehouse/action/approve" cssClass="form-group">
                    <form:hidden path="id"/>
                    <form:hidden path="inventory"/>
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
                            <label>Flial</label>
                        </div>
                        <div class="col-sm-6">
                            <label id="warehouse_label"></label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 text-right">
                            <label>Hərəkət</label>
                        </div>
                        <div class="col-sm-6">
                            <label id="action_label"></label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 text-right">
                            <label>Təchizatçı</label>
                        </div>
                        <div class="col-sm-6">
                            <label id="supplier_label"></label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 text-right">
                            <label>Miqdar</label>
                        </div>
                        <div class="col-sm-6">
                            <label id="amount_label"></label>
                        </div>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#transfer-approve-form'));">Bəli, təsdiq edirəm!</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="consolidate-modal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Təhkim etmə</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="form-consolidate" method="post" action="/warehouse/action/consolidate" cssClass="form-group">
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
                        <form:label path="employee">Əməkdaş</form:label>
                        <form:select  path="employee" cssClass="custom-select form-control">
                            <form:option value=""></form:option>
                            <c:forEach var="itemGroup" items="${employees}" varStatus="itemGroupIndex">
                                <optgroup label="${itemGroup.key}">
                                    <form:options items="${itemGroup.value}" itemLabel="person.fullName" itemValue="id"/>
                                </optgroup>
                            </c:forEach>
                        </form:select>
                        <form:errors path="employee" cssClass="alert-danger"/>
                    </div>
                    <div class="form-group">
                        <form:label path="amount">Say</form:label>
                        <div class="input-group" >
                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-calculator"></i></span></div>
                            <form:input path="amount" cssClass="form-control" placeholder="Say daxil edin"/>
                        </div>
                        <form:errors path="amount" cssClass="alert-danger"/>
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
                <form:form modelAttribute="form" id="form-return" method="post" action="/warehouse/action/return" cssClass="form-group">
                    <form:hidden path="id"/>
                    <form:hidden path="inventory.id"/>
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
                        <div class="input-group" >
                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-calculator"></i></span></div>
                            <form:input path="amount" cssClass="form-control" placeholder="Sayı daxil edin"  />
                        </div>
                        <form:errors path="amount" cssClass="alert-danger"/>
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

<div class="modal fade" id="cancellation-modal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Silinmə</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="form-cancellation" method="post" action="/warehouse/action/cancellation" cssClass="form-group">
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
                        <div class="input-group" >
                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-calculator"></i></span></div>
                            <form:input path="amount" cssClass="form-control" placeholder="Sayı daxil edin"  />
                        </div>
                        <form:errors path="amount" cssClass="alert-danger"/>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#form-cancellation'));">Yadda saxla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<script>
    function transfer(form, data, modal){
        try {
            data = data.replace(/\&#034;/g, '"');
            var obj = jQuery.parseJSON(data);
            console.log(obj);
            $(form).find("input[name='from']").val(obj["organization"]["name"]);
            $(form).find("input[name='fromOrganization']").val(obj["organization"]["id"]);
            $(form).find("input[name='id']").val(obj["id"]);
            $(form).find("input[name='inventory']").val(obj["inventory"]["id"]);
            $(form).find("input[name='action']").val(obj["action"]["id"]);
            if(obj["supplier"]!==null){
                $(form).find("input[name='supplier']").val(obj["supplier"]["id"]);
            }
            $('#' + modal).modal('toggle');
        } catch (e) {
            console.error(e);
        }
    }

    function consolidate(form, data, modal){
        try {
            data = data.replace(/\&#034;/g, '"');
            var obj = jQuery.parseJSON(data);
            console.log(obj);

            if(obj["employee"]!=null){
                $("#employee option[value="+obj["employee"]["id"]+"]").attr("selected", "selected");
            }
            $(form).find("#id").val(obj["id"]);
            $(form).find("#inventory_name").text(obj["inventory"]["name"]);
            $(form).find("#barcode_label").text(obj["inventory"]["barcode"]);
            $(form).find("#warehouse_label").text(obj["organization"]["name"]);
            $('#' + modal).modal('toggle');
        } catch (e) {
            console.error(e);
        }
    }

    function approve(form, modal, id, inventory_id, inventory_name, barcode_label, warehouse_label, action_label, supplier_label, amount_label){
        $(form).find("#id").val(id);
        $(form).find("#inventory").val(inventory_id);
        $(form).find("#inventory_name").text(inventory_name);
        $(form).find("#barcode_label").text(barcode_label);
        $(form).find("#warehouse_label").text(warehouse_label);
        $(form).find("#action_label").text(action_label);
        $(form).find("#supplier_label").text(supplier_label);
        $(form).find("#amount_label").text(amount_label);
        $(modal).find(".modal-title").html('Təsdiq et!');
        $(modal).modal('toggle');
    }

    function returnOperation(form, data, modal){
        try {
            data = data.replace(/\&#034;/g, '"');
            var obj = jQuery.parseJSON(data);
            console.log(obj);
            $(form).find("input[name='amount']").val(obj["amount"]);
            $(form).find("#id").val(obj["id"]);
            $(form).find("#inventory_name").text(obj["inventory"]["name"]);
            $(form).find("#barcode_label").text(obj["inventory"]["barcode"]);
            $(form).find("#warehouse_label").text(obj["organization"]["name"]);
            $('#' + modal).modal('toggle');
        } catch (e) {
            console.error(e);
        }
    }
</script>

<script>
    <c:if test="${edit.status}">
    $('#datatable tbody').on('dblclick', 'tr', function () {
        edit($('#form'), $(this).attr('data'), 'modal-operation', 'Redaktə');
    });
    </c:if>

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

    $( "#form-consolidate" ).validate({
        rules: {
            employee: {
                required: true
            },
            amount: {
                required: true,
                digits: true,
                min: 1
            }
        },
        invalidHandler: function(event, validator) {
            KTUtil.scrollTop();
            swal.close();
        },
    });

    $( "#form" ).validate({
        rules: {
            organization: {
                required: true
            },
            amount: {
                required: true,
                digits: true,
                min: 1
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

    $( "#form-return" ).validate({
        rules: {
            id: {
                required: true
            },
            inventory: {
                required: true
            },
            amount: {
                required: true,
                digits: true,
                min: 1
            }
        },
        invalidHandler: function(event, validator) {
            KTUtil.scrollTop();
            swal.close();
        },
    });
</script>


