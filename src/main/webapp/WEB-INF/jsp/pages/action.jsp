<%--
  Created by IntelliJ IDEA.
  User: irkan.ahmadov
  Date: 01.09.2019
  Time: 1:22
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <th>Anbar</th>
        <th>Hərəkət</th>
        <th>Təchizatçı</th>
        <th>İnventar</th>
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
            <td><c:out value="${t.warehouse.name}" /></td>
            <td><c:out value="${t.action.name}" /></td>
            <td><c:out value="${t.supplier.name}" /></td>
            <td><c:out value="${t.inventory.name}" /></td>
            <td><c:out value="${t.amount}" /></td>
            <td><fmt:formatDate value = "${t.createdDate}" pattern = "dd.MM.yyyy" /></td>
            <td><c:out value="${t.employee.person.fullName}" /></td>
            <td nowrap class="text-center">
                <c:set var="view" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'view')}"/>
                <c:choose>
                    <c:when test="${view.status}">
                        <a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${view.object.name}"/>">
                            <i class="la <c:out value="${view.object.icon}"/>"></i>
                        </a>
                    </c:when>
                </c:choose>
                <c:set var="approve" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'approve')}"/>
                <c:choose>
                    <c:when test="${approve.status}">
                        <c:if test="${!t.approve}">
                            <a href="javascript:approve($('#transfer-approve-form'), $('#transfer-approve-modal'), '<c:out value="${t.id}" />', '<c:out value="${t.inventory.id}" />', '<c:out value="${t.inventory.name}" />', '<c:out value="${t.inventory.barcode}" />', '<c:out value="${t.warehouse.name}" />', '<c:out value="${t.action.name}" />', '<c:out value="${t.supplier.name}" />', '<c:out value="${t.amount}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${approve.object.name}"/>">
                                <i class="<c:out value="${approve.object.icon}"/>"></i>
                            </a>
                        </c:if>
                    </c:when>
                </c:choose>
                <c:set var="transfer" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'transfer')}"/>
                <c:choose>
                    <c:when test="${transfer.status}">
                        <c:if test="${t.approve}">
                            <a href="javascript:transfer($('#form'), '<c:out value="${utl:toJson(t)}" />', 'transfer-modal-operation');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${transfer.object.name}"/>">
                                <i class="<c:out value="${transfer.object.icon}"/>"></i>
                            </a>
                        </c:if>
                    </c:when>
                </c:choose>
                <c:set var="return1" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'return')}"/>
                <c:choose>
                    <c:when test="${return1.status}">
                        <c:if test="${t.action.attr1 eq 'consolidate'}">
                            <a href="javascript:returnOperation($('#form-return'), '<c:out value="${utl:toJson(t)}" />', 'return-modal');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${return1.object.name}"/>">
                                <i class="<c:out value="${return1.object.icon}"/>"></i>
                            </a>
                        </c:if>
                    </c:when>
                </c:choose>
                <c:set var="consolidate" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'consolidate')}"/>
                <c:choose>
                    <c:when test="${consolidate.status}">
                        <c:if test="${t.approve}">
                            <a href="javascript:consolidate($('#form-consolidate'), '<c:out value="${utl:toJson(t)}" />', 'consolidate-modal');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${consolidate.object.name}"/>">
                                <i class="<c:out value="${consolidate.object.icon}"/>"></i>
                            </a>
                        </c:if>
                    </c:when>
                </c:choose>
                <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
                <c:choose>
                    <c:when test="${edit.status}">
                        <a href="javascript:edit($('#form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${edit.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${edit.object.name}"/>">
                            <i class="<c:out value="${edit.object.icon}"/>"></i>
                        </a>
                    </c:when>
                </c:choose>
                <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
                <c:choose>
                    <c:when test="${delete.status}">
                        <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.action.name}" /> / <c:out value="${t.warehouse.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
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


<div class="modal fade" id="transfer-modal-operation" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
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
                    <input type="hidden" name="fromWarehouse"/>
                    <div class="form-group">
                        <label for="from">Haradan?</label>
                        <input name="from" id="from" class="form-control" readonly/>
                    </div>
                    <div class="form-group">
                        <form:label path="warehouse">Haraya?</form:label>
                        <form:select  path="warehouse" cssClass="custom-select form-control">
                            <form:options items="${warehouses}" itemLabel="name" itemValue="id" />
                        </form:select>
                        <form:errors path="warehouse" cssClass="alert-danger"/>
                    </div>
                    <div class="form-group">
                        <form:label path="amount">Say</form:label>
                        <form:input path="amount" cssClass="form-control" placeholder="Sayı daxil edin"  />
                        <form:errors path="amount" cssClass="alert alert-danger"/>
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
                            <label>Anbar</label>
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
                            <form:options items="${employees}" itemLabel="person.fullName" itemValue="id" />
                        </form:select>
                        <form:errors path="warehouse" cssClass="alert-danger"/>
                    </div>
                    <div class="form-group">
                        <form:label path="amount">Say</form:label>
                        <form:input path="amount" cssClass="form-control" placeholder="Sayı daxil edin"  />
                        <form:errors path="amount" cssClass="alert alert-danger"/>
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
    function transfer(form, data, modal){
        try {
            data = data.replace(/\&#034;/g, '"');
            var obj = jQuery.parseJSON(data);
            console.log(obj);
            $(form).find("input[name='from']").val(obj["warehouse"]["name"]);
            $(form).find("input[name='fromWarehouse']").val(obj["warehouse"]["id"]);
            $(form).find("input[name='id']").val(obj["id"]);
            $(form).find("input[name='inventory']").val(obj["inventory"]["id"]);
            $(form).find("input[name='action']").val(obj["action"]["id"]);
            $(form).find("input[name='supplier']").val(obj["supplier"]["id"]);
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
            $(form).find("#warehouse_label").text(obj["warehouse"]["name"]);
            $('#' + modal).modal('toggle');
        } catch (e) {
            console.error(e);
        }
    }
</script>


