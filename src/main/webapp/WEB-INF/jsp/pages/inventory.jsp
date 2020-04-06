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
                        <form:form modelAttribute="filter" id="filter" method="post" action="/warehouse/inventory/filter">
                            <form:hidden path="organization"/>
                            <form:hidden path="active"/>
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
                                                <form:label path="group">Qrup</form:label>
                                                <form:select  path="group.id" cssClass="custom-select form-control">
                                                    <form:option value=""></form:option>
                                                    <form:options items="${inventory_groups}" itemLabel="name" itemValue="id" />
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="name">Ad</form:label>
                                                <form:input path="name" cssClass="form-control" placeholder="Adı daxil edin"/>
                                                <form:errors path="name" cssClass="alert-danger control-label"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="barcode">Barkod</form:label>
                                                <form:input path="barcode" cssClass="form-control" placeholder="Barkodu daxil edin"/>
                                                <form:errors path="barcode" cssClass="alert-danger control-label"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="description">Açıqlama</form:label>
                                                <form:input path="description" cssClass="form-control" placeholder="Açıqlamanı daxil edin"/>
                                                <form:errors path="description" cssClass="alert-danger control-label"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="inventoryDateFrom">Tarixdən</form:label>
                                                <div class="input-group date" >
                                                    <form:input path="inventoryDateFrom" autocomplete="off" date_="date_" cssClass="form-control datepicker-element" placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                                        <span class="input-group-text">
                                                            <i class="la la-calendar"></i>
                                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="inventoryDateFrom" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="inventoryDate">Tarixədək</form:label>
                                                <div class="input-group date" >
                                                    <form:input path="inventoryDate" autocomplete="off" date_="date_" cssClass="form-control datepicker-element" placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                    <span class="input-group-text">
                                        <i class="la la-calendar"></i>
                                    </span>
                                                    </div>
                                                </div>
                                                <form:errors path="inventoryDate" cssClass="control-label alert-danger" />
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
                            <c:set var="export" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'export')}"/>
                            <c:set var="detail" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'detail')}"/>
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="group_table">
                                <thead>
                                <tr>
                                    <th>№</th>
                                    <th>ID</th>
                                    <th>Qrup</th>
                                    <th>Ad</th>
                                    <th>Açıqlama</th>
                                    <th>Barkod</th>
                                    <th>Miqdar</th>
                                    <th>Təsdiq gözləyir</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list.content}" varStatus="loop">
                                    <tr data="<c:out value="${t.id}" />">
                                        <td>${loop.index + 1}</td>
                                        <td><c:out value="${t.id}" /></td>
                                        <td><c:out value="${t.group.name}" /></td>
                                        <td><c:out value="${t.name}" /></td>
                                        <td><c:out value="${t.description}" /></td>
                                        <td>
                                            <span class="barcode">
                                            <a href="javascript:copyToClipboard('<c:out value="${t.barcode}" />')" class="kt-link kt-font-lg kt-font-bold kt-margin-t-5"><c:out value="${t.barcode}"/></a>
                                            </span>
                                        <c:if test="${export.status}">
                                            <a href="javascript:printBarcode('<c:out value="${t.name}" />', '<c:out value="${t.barcode}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${export.object.name}"/>">
                                                <i class="la <c:out value="${export.object.icon}"/>"></i>
                                            </a>
                                        </c:if>
                                        </td>
                                        <td>
                                            <c:set var="ia" value="${utl:calculateInventoryAmount(t.actions, sessionScope.organization.id)}"/>
                                            <span class="kt-badge kt-badge--success kt-badge--inline kt-badge--pill"><c:out value="${ia.allItemsCount}"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="kt-badge kt-badge--danger kt-badge--inline kt-badge--pill"><c:out value="${ia.oldItemsCount}"/></span>
                                        </td>
                                        <td class="text-center">
                                            <c:set var="approveCount" value="${utl:calculateApproveOperationCount(t.actions, sessionScope.organization.id)}"/>
                                            <c:if test="${approveCount gt 0}">
                                                <c:out value="${approveCount}"/> əməliyyat
                                            </c:if>
                                        </td>
                                        <td nowrap class="text-center">
                                            <c:if test="${detail.status}">
                                                <a href="/warehouse/action/<c:out value="${t.id}"/>" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${detail.object.name}"/>">
                                                    <i class="la <c:out value="${detail.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${delete.status}">
                                                <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
                                                    <i class="<c:out value="${delete.object.icon}"/>"></i>
                                                </a>
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

<div class="modal fade" id="modal-operation" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Yeni sorğu yarat</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="form" method="post" action="/warehouse/inventory" cssClass="form-group">
                    <form:hidden path="id"/>
                    <form:hidden path="organization"/>
                    <form:hidden path="active"/>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="group">Qrup</form:label>
                                <form:select  path="group" cssClass="custom-select form-control">
                                    <form:option value=""></form:option>
                                    <form:options items="${inventory_groups}" itemLabel="name" itemValue="id" />
                                </form:select>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="actions[0].supplier">Tədarükçü</form:label>
                                <form:select  path="actions[0].supplier" cssClass="custom-select form-control">
                                    <form:option value=""></form:option>
                                    <form:options items="${suppliers}" itemLabel="name" itemValue="id" />
                                </form:select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-7">
                            <div class="form-group">
                                <form:label path="name">Ad</form:label>
                                <form:input path="name" cssClass="form-control" placeholder="Adı daxil edin"/>
                                <form:errors path="name" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                        <div class="col-md-5">
                            <div class="form-group">
                                <form:label path="actions[0].amount">Say</form:label>
                                <div class="input-group" >
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-calculator"></i></span></div>
                                    <form:input path="actions[0].amount" cssClass="form-control" placeholder="Say daxil edin"/>
                                </div>
                                <form:errors path="actions[0].amount" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="description">Açıqlama</form:label>
                                <form:textarea path="description" cssClass="form-control" placeholder="Açıqlamanı daxil edin" />
                                <form:errors path="description" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                        <div class="col-md-6 text-center">
                            <div class="form-group">
                                <form:label path="barcode">Barkod forma</form:label>
                                <img src="<c:url value="/assets/media/barcode.png" />" style="width: 210px; height: 60px;">
                                <form:hidden path="barcode" cssClass="form-control" placeholder="Barkodu daxil edin" readonly="true"/>
                                <form:errors path="barcode" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
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

<div id="barcodePrint" style="display: none; margin: 10px;">
    <div id="barcodeTarget" style="margin: 10px;"></div>
</div>

<script src="<c:url value="/assets/js/demo4/pages/crud/datatables/advanced/row-grouping.js" />" type="text/javascript"></script>
<script src="<c:url value="/assets/js/jquery-barcode.js" />" type="text/javascript"></script>
<script>
    function printBarcode(description, barcode) {
        generateBarcode('code128', barcode, 'css');
        var divToPrint=document.getElementById('barcodePrint');
        var newWin=window.open('','Print-Window');
        newWin.document.open();
        newWin.document.write('<html><body style="width: 220px; height: 151px; padding-left: 55px; padding-right: 5px; padding-top: 30px;" onload="window.print()"><div style="text-align: center;margin-bottom: 3px; font-size: 12px; font-weight: bold">'+description+'</div>'+divToPrint.innerHTML+'</body></html>');
        newWin.document.close();
        setTimeout(function(){newWin.close();},10);
    }

    function generateBarcode(btype, value, renderer){
        var settings = {
            output:renderer,
            bgColor: '#FFFFFF',
            color: '#000000',
            barWidth: 2,
            barHeight: 55,
            moduleSize: 50,
            fontSize: 18,
            marginHRI: 1,
            posX: 0,
            posY: 10,
            addQuietZone: 10
        };
        $("#barcodeTarget").html("").show().barcode(value, btype, settings);
    }

    <c:if test="${detail.status}">
    $('#group_table tbody').on('dblclick', 'tr', function () {
        swal.showLoading();
        location.href = '/warehouse/action/'+ $(this).attr('data');
        window.reload();
    });
    </c:if>

    $( "#form" ).validate({
        rules: {
            group: {
                required: true
            },
            "actions[0].amount": {
                required: true,
                digits: true,
                min: 1
            },
            name: {
                required: true
            },
            "actions[0].supplier": {
                required: true
            }
        },
        invalidHandler: function(event, validator) {
                    KTUtil.scrollTop();
            swal.close();
        },
    });

    $("input[name='actions[0].amount']").inputmask('decimal', {
        rightAlignNumerics: false
    });

</script>


