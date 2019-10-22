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
<link href="<c:url value="/assets/external/bootstrap-duallistbox-4/dist/bootstrap-duallistbox.min.css" />" rel="stylesheet" type="text/css"/>

<div class="kt-container  kt-grid__item kt-grid__item--fluid">
    <div class="row">
        <div class="col-lg-12">
            <div class="kt-portlet kt-portlet--mobile">
                <div class="kt-portlet__body">
                    <c:choose>
                        <c:when test="${not empty list}">
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="group_table">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Ad</th>
                                    <th>Tipi</th>
                                    <th>Atribut#1</th>
                                    <th>Atribut#2</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list}" varStatus="loop">
                                    <tr>
                                        <td><c:out value="${t.id}" /></td>
                                        <td><c:out value="${t.name}" /></td>
                                        <th><c:out value="${t.dictionaryType.name}" /></th>
                                        <td><c:out value="${t.attr1}" /></td>
                                        <td><c:out value="${t.attr2}" /></td>
                                        <td nowrap class="text-center">
                                            <c:set var="view" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'view')}"/>
                                            <c:choose>
                                                <c:when test="${view.status}">
                                                    <a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${view.object.name}"/>">
                                                        <i class="la <c:out value="${view.object.icon.name}"/>"></i>
                                                    </a>
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
                                                    <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
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
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Yeni satış qrupu yarat</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="form" method="post" action="/sale/sale-group" cssClass="form-group">
                    <form:input type="hidden" path="id"/>
                    <div class="form-group">
                        <form:label path="name">Ad</form:label>
                        <form:input path="name" cssClass="form-control" placeholder="Adı daxil edin"/>
                        <form:errors path="name" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="form-group">
                        <form:select  path="saleGroupEmployees" cssClass="kt-dual-listbox form-control" multiple="multiple">
                            <form:options items="${employees}" itemLabel="person.fullName" itemValue="id" />
                        </form:select>
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

<script src="<c:url value="/assets/js/demo4/pages/crud/datatables/advanced/row-grouping.js" />" type="text/javascript"></script>
<script src="<c:url value="/assets/external/bootstrap-duallistbox-4/dist/jquery.bootstrap-duallistbox.js" />" type="text/javascript"></script>

<script>
    var KTDualListbox = function () {
        var initDualListbox = function () {
            var listBoxes = $('.kt-dual-listbox');
            listBoxes.each(function(){
                var $this = $(this);
                var id = '#' + $this.attr('id');
                var availableTitle = ($this.attr('data-available-title') != null) ? $this.attr('data-available-title') : 'Mümkün variantlar';
                var selectedTitle = ($this.attr('data-selected-title') != null) ? $this.attr('data-selected-title') : 'Seçilmiş variantlar';
                var addLabel = ($this.attr('data-add') != null) ? $this.attr('data-add') : 'Əlavə et';
                var removeLabel = ($this.attr('data-remove') != null) ? $this.attr('data-remove') : 'Sil';
                var addAllLabel = ($this.attr('data-add-all') != null) ? $this.attr('data-add-all') : 'Bütün əlavə et';
                var removeAllLabel = ($this.attr('data-remove-all') != null) ? $this.attr('data-remove-all') : 'Bütün sil';
                var options = [];
                $this.children('option').each(function(){
                    var value = $(this).val();
                    var label = $(this).text();
                    var selected = ($(this).is(':selected')) ? true : false;
                    options.push({ text: label, value: value, selected: selected });
                });
                var search = ($this.attr('data-search') != null) ? $this.attr('data-search') : "";
                $this.empty();
                var dualListBox = new DualListbox(id,{
                    addEvent: function(value) {
                        console.log(value);
                        console.log(id);
                    },
                    removeEvent: function(value) {
                        console.log(value);
                        console.log(id);
                    },
                    availableTitle: availableTitle,
                    selectedTitle: selectedTitle,
                    addButtonText: addLabel,
                    removeButtonText: removeLabel,
                    addAllButtonText: addAllLabel,
                    removeAllButtonText: removeAllLabel,
                    options: options
                });
                if (search == "false"){
                    dualListBox.search.classList.add('dual-listbox__search--hidden');
                }
            });
        };

        return {
            init: function() {
                initDualListbox();
            }
        };
    }();

    /*KTUtil.ready(function() {
        KTDualListbox.init();
    });*/

    $(function(){
        var dual = $('.kt-dual-listbox').bootstrapDualListbox({
            nonSelectedListLabel: 'Seçilməmiş',
            selectedListLabel: 'Seçilmiş',
            preserveSelectionOnMove: 'moved',
            moveOnSelect: false,
            infoText: 'Göstərilir {0}',
            filterPlaceHolder: 'Axtar...',
            infoTextEmpty: 'Boş siyahı',
            eventMoveOverrid: true,
            eventMoveAllOverride: true,
            setMoveOnSelect(value, refresh){
                alert(value);
            }
        });
        dual.bootstrapDualListbox('refresh');
    })
</script>



