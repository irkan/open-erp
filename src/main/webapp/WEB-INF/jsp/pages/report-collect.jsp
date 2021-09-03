<%--
  Created by IntelliJ IDEA.
  User: irkan.ahmadov
  Date: 20.09.2019
  Time: 1:22
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="utl" uri="/WEB-INF/tld/Util.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="random" class="java.util.Random" scope="application"/>
<div class="kt-container  kt-grid__item kt-grid__item--fluid">
    <c:set var="filter" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'filter')}"/>
    <c:if test="${filter.status}">
        <div class="accordion  accordion-toggle-arrow mb-2" id="accordionFilter">
            <div class="card" style="border-radius: 4px;">
                <div class="card-header">
                    <div class="card-title w-100" data-toggle="collapse" data-target="#filterContent" aria-expanded="true" aria-controls="collapseOne4">
                        <div class="row w-100">
                            <div class="col-3">
                                <i class="<c:out value="${filter.object.icon}"/>"></i>
                            </div>
                            <div class="col-6 text-center" style="letter-spacing: 10px;">
                                <c:out value="${filter.object.name}"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="filterContent" class="collapse show" aria-labelledby="headingOne" data-parent="#accordionFilter">
                    <div class="card-body">
                        <form:form modelAttribute="filter" id="filter" method="post" action="/report/api/report-collect">
                            <div class="row">
                                <div class="col-md-10">
                                    <div class="row">
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="string1">Struktur</form:label>
                                                <form:select path="string1" cssClass="custom-select form-control">
                                                    <form:option value=" ">Bütün fliallar üzrə</form:option>
                                                    <form:options items="${organizations}" itemLabel="name" itemValue="reportCondition"/>
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="string2">Satış növü</form:label>
                                                <form:select path="string2" cssClass="custom-select form-control">
                                                    <form:option value=" ">Bütün növlər üzrə</form:option>
                                                    <form:option value=" and s1.is_service=0 ">Satış</form:option>
                                                    <form:option value=" and s1.is_service=1 ">Servis</form:option>
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="string3">Müqavilə növü</form:label>
                                                <form:select path="string3" cssClass="custom-select form-control">
                                                    <form:option value=" ">Bütün müqavilələr üzrə</form:option>
                                                    <form:option value=" and p1.is_cash=0 ">Kredit</form:option>
                                                    <form:option value=" and p1.is_cash=1 ">Nağd</form:option>
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="string4">Müqavilə statusu</form:label>
                                                <form:select path="string4" cssClass="custom-select form-control">
                                                    <form:option value=" and s1.is_saled=0 ">Davam edir</form:option>
                                                    <form:option value=" and s1.is_saled=1 ">Bitib</form:option>
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="string5">Müqavilə təsdiqi</form:label>
                                                <form:select path="string5" cssClass="custom-select form-control">
                                                    <form:option value=" and s1.is_returned=0 ">Aktual</form:option>
                                                    <form:option value=" and s1.is_returned=1 ">Qaytarılmış</form:option>
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="string6">Müqavilə statusu</form:label>
                                                <form:select path="string6" cssClass="custom-select form-control">
                                                    <form:option value=" and s1.is_active=1 ">Aktiv</form:option>
                                                    <form:option value=" and s1.is_active=0 ">Silinmiş</form:option>
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="string8">Məhkəmə statusu</form:label>
                                                <form:select path="string8" cssClass="custom-select form-control">
                                                    <form:option value=" ">Bütün</form:option>
                                                    <form:option value=" and s1.is_court=1 ">Məhkəmədədir</form:option>
                                                    <form:option value=" and s1.is_court=0 ">Məhkəmədə deyil</form:option>
                                                </form:select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-2 text-right">
                                    <div class="form-group pt-3">
                                        <button onclick="submitFilter()" class="btn btn-warning btn-elevate btn-icon-sm btn-block mt-2">
                                            <i class="la la-search"></i> Hesabla
                                        </button>
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
                <div id="chart" class="pl-1 pr-3 pt-2"></div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12 border-0">
            <div class="card border-0 mb-3">
                <div class="card-body <c:out value="${random.nextInt(5) eq 1 ? 'kt-bg-light-danger' : random.nextInt(5) eq 2 ? 'kt-bg-light-warning' : random.nextInt(5) eq 3 ? 'kt-bg-light-info' : random.nextInt(5) eq 4 ? 'kt-bg-light-success' : random.nextInt(5) eq 5 ? 'kt-bg-light-primary' : 'kt-bg-light-brand'}"/>">
                    <h6 class="card-title font-weight-bolder text-center" style="letter-spacing: 2px">Yığım</h6>
                    <hr/>
                    <div id="pie-chart" class="d-flex justify-content-center"></div>
                </div>
            </div>
        </div>
        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12 border-0">
            <div class="card border-0 mb-3">
                <div class="card-body <c:out value="${random.nextInt(5) eq 1 ? 'kt-bg-light-danger' : random.nextInt(5) eq 2 ? 'kt-bg-light-warning' : random.nextInt(5) eq 3 ? 'kt-bg-light-info' : random.nextInt(5) eq 4 ? 'kt-bg-light-success' : random.nextInt(5) eq 5 ? 'kt-bg-light-primary' : 'kt-bg-light-brand'}"/>">
                    <h6 class="card-title font-weight-bolder text-center" style="letter-spacing: 2px">Yığım</h6>
                    <hr/>
                    <div id="pie-chart2" class="d-flex justify-content-center"></div>
                </div>
            </div>
        </div>
        <div class="col-xl-4 col-lg-4 col-md-4 col-sm-4 col-4 border-0">
            <div class="card border-0 mb-3">
                <div class="card-body <c:out value="${random.nextInt(5) eq 1 ? 'kt-bg-light-danger' : random.nextInt(5) eq 2 ? 'kt-bg-light-warning' : random.nextInt(5) eq 3 ? 'kt-bg-light-info' : random.nextInt(5) eq 4 ? 'kt-bg-light-success' : random.nextInt(5) eq 5 ? 'kt-bg-light-primary' : 'kt-bg-light-brand'}"/>">
                    <h6 class="card-title font-weight-bolder text-center" style="letter-spacing: 2px">Yığım</h6>
                    <hr/>
                    <div id="bar-chart" class="d-flex justify-content-center"></div>
                </div>
            </div>
        </div>
        <div class="col-xl-5 col-lg-5 col-md-5 col-sm-5 col-5 border-0">
            <div class="card border-0 mb-3">
                <div class="card-body <c:out value="${random.nextInt(5) eq 1 ? 'kt-bg-light-danger' : random.nextInt(5) eq 2 ? 'kt-bg-light-warning' : random.nextInt(5) eq 3 ? 'kt-bg-light-info' : random.nextInt(5) eq 4 ? 'kt-bg-light-success' : random.nextInt(5) eq 5 ? 'kt-bg-light-primary' : 'kt-bg-light-brand'}"/>">
                    <h6 class="card-title font-weight-bolder text-center" style="letter-spacing: 2px">Yığım həcmləri</h6>
                    <hr/>
                    <div id="bar-chart-2" class="d-flex justify-content-center"></div>
                </div>
            </div>
        </div>
        <div class="col-xl-3 col-lg-3 col-md-3 col-sm-3 col-3 border-0">
            <div class="card border-0 mb-3">
                <div class="card-body <c:out value="${random.nextInt(5) eq 1 ? 'kt-bg-light-danger' : random.nextInt(5) eq 2 ? 'kt-bg-light-warning' : random.nextInt(5) eq 3 ? 'kt-bg-light-info' : random.nextInt(5) eq 4 ? 'kt-bg-light-success' : random.nextInt(5) eq 5 ? 'kt-bg-light-primary' : 'kt-bg-light-brand'}"/>">
                    <h6 class="card-title font-weight-bolder text-center" style="letter-spacing: 2px">Yığım sayları</h6>
                    <hr/>
                    <div id="bar-chart-3" class="d-flex justify-content-center"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/apexcharts"></script>
<script>
    $(function(){
        var options = {
            series: [{
                name: 'Ümumi məbləğ',
                type: 'line',
                data: []
            }, {
                name: 'Gecikmiş məbləğ',
                type: 'line',
                data: []
            }, {
                name: 'Problemli məbləğ',
                type: 'line',
                data: []
            }, {
                name: 'Məhkəmədə məbləğ',
                type: 'line',
                data: []
            }, {
                name: 'Ümumi say',
                type: 'column',
                data: []
            }, {
                name: 'Gecikmiş say',
                type: 'column',
                data: []
            }, {
                name: 'Problemli say',
                type: 'column',
                data: []
            }, {
                name: 'Məhkəmədə say',
                type: 'column',
                data: []
            }],
            chart: {
                height: $( window ).height()-3*$("#accordionFilter").height()>350?$( window ).height()-2*$("#accordionFilter").height():350,
                type: 'line',
                stacked: false
            },
            dataLabels: {
                enabled: false
            },
            stroke: {
                width: [4, 4, 4, 4, 1, 1, 1, 1]
            },
            title: {
                text: 'XYZ - 3 ölçülü yığım analizi',
                align: 'left',
                offsetX: 50
            },
            xaxis: {
                categories: [],
            },
            yaxis: [
                {
                    seriesName: 'Ümumi məbləğ',
                    axisTicks: {
                        show: true,
                    },
                    axisBorder: {
                        show: true,
                        color: '#008FFB'
                    },
                    labels: {
                        style: {
                            colors: '#008FFB',
                        }
                    },
                    title: {
                        style: {
                            color: '#008FFB',
                        }
                    },
                    tooltip: {
                        enabled: true
                    }
                }/*,
                {
                    seriesName: 'Gecikmiş məbləğ',
                    show: false
                },
                {
                    seriesName: 'Problemli məbləğ',
                    show: false
                },
                {
                    seriesName: 'Ümumi say',
                    opposite: true,
                    axisTicks: {
                        show: true,
                    },
                    axisBorder: {
                        show: true,
                        color: '#fb406b'
                    },
                    labels: {
                        style: {
                            colors: '#fb406b',
                        }
                    },
                    title: {
                        style: {
                            color: '#fb406b',
                        }
                    },
                    tooltip: {
                        enabled: true
                    }
                },
                {
                    seriesName: 'Gecikmiş say',
                    show: false
                },
                {
                    seriesName: 'Problemli say',
                    show: false
                }*/
            ],
            tooltip: {
                fixed: {
                    enabled: true,
                    position: 'topLeft', // topRight, topLeft, bottomRight, bottomLeft
                    offsetY: 30,
                    offsetX: 60
                },
            },
            legend: {
                horizontalAlign: 'left',
                offsetX: 20
            }
        };

        var chart = new ApexCharts(document.querySelector("#chart"), options);
        chart.render();






        var totalSum=0;
        var pieoptions = {
            series: [0, 0],
            chart: {type: 'donut'},
            plotOptions: {
                pie: {
                    donut: {
                        labels: {
                            show: true,
                            name: {
                                fontSize: '22px',
                                fontWeight: 'bold',
                                color: '#088fd7'
                            },
                            value: {
                                fontSize: '16px',
                                fontWeight: 'bold',
                                color: '#FF325B'
                            },
                            total: {
                                show: true,
                                label: 'Cəmi',
                                formatter: function () {
                                    return totalSum;
                                }
                            }
                        }
                    }
                }
            },
            labels: ['Yığım məbləği', 'Qalıq borc'],
            legend: {
                position: 'bottom'
            },
            responsive: [
                {
                    breakpoint: 480,
                    options: {
                        chart: {
                            width: 200
                        }, legend: {
                            position: 'bottom'
                        }
                    }
                }
            ]
        };

        var piechart = new ApexCharts(document.querySelector("#pie-chart"), pieoptions);
        piechart.render();

        var totalSum2=0;
        var pieoptions2 = {
            series: [0, 0],
            chart: {type: 'donut'},
            plotOptions: {
                pie: {
                    donut: {
                        labels: {
                            show: true,
                            name: {
                                fontSize: '22px',
                                fontWeight: 'bold',
                                color: '#088fd7'
                            },
                            value: {
                                fontSize: '16px',
                                fontWeight: 'bold',
                                color: '#FF325B'
                            },
                            total: {
                                show: true,
                                label: 'Cəmi',
                                formatter: function () {
                                    return totalSum2;
                                }
                            }
                        }
                    }
                }
            },
            labels: ['Yığım məbləği', 'Qalıq borc'],
            legend: {
                position: 'bottom'
            },
            responsive: [
                {
                    breakpoint: 480,
                    options: {
                        chart: {
                            width: 200
                        }, legend: {
                            position: 'bottom'
                        }
                    }
                }
            ]
        };

        var piechart2 = new ApexCharts(document.querySelector("#pie-chart2"), pieoptions2);
        piechart2.render();

        const colours = ['#5388f4', '#E91E63', '#9C27B0'];
        var baroptions = {
            series: [{
                data: [0]
            }],
            chart: {
                height: 350,
                type: 'bar',
                events: {
                    click: function(chart, w, e) {
                        // console.log(chart, w, e)
                    }
                }
            },
            colors: colours,
            plotOptions: {
                bar: {
                    columnWidth: '45%',
                    distributed: true
                }
            },
            dataLabels: {
                enabled: false
            },
            legend: {
                show: false
            },
            xaxis: {
                categories: [
                    'Aylıq yığım həcmi',
                ],
                labels: {
                    style: {
                        colors: colours,
                        fontSize: '12px'
                    }
                }
            }
        };

        var baroptions2 = {
            series: [{
                data: [0]
            }],
            chart: {
                height: 350,
                type: 'bar',
                events: {
                    click: function(chart, w, e) {
                        // console.log(chart, w, e)
                    }
                }
            },
            colors: colours,
            plotOptions: {
                bar: {
                    columnWidth: '45%',
                    distributed: true
                }
            },
            dataLabels: {
                enabled: false
            },
            legend: {
                show: false
            },
            xaxis: {
                categories: [
                    'Satış və yığım həcmi',
                ],
                labels: {
                    style: {
                        colors: colours,
                        fontSize: '12px'
                    }
                }
            }
        };

        var baroptions3 = {
            series: [{
                data: [0]
            }],
            chart: {
                height: 350,
                type: 'bar',
                events: {
                    click: function(chart, w, e) {
                        // console.log(chart, w, e)
                    }
                }
            },
            colors: colours,
            plotOptions: {
                bar: {
                    columnWidth: '45%',
                    distributed: true
                }
            },
            dataLabels: {
                enabled: false
            },
            legend: {
                show: false
            },
            xaxis: {
                categories: [
                    'Satış və yığım say',
                ],
                labels: {
                    style: {
                        colors: colours,
                        fontSize: '12px'
                    }
                }
            }
        };

        var barchart = new ApexCharts(document.querySelector("#bar-chart"), baroptions);
        barchart.render();

        var barchart2 = new ApexCharts(document.querySelector("#bar-chart-2"), baroptions2);
        barchart2.render();

        var barchart3 = new ApexCharts(document.querySelector("#bar-chart-3"), baroptions3);
        barchart3.render();

        $("#filter").ajaxForm({
            beforeSubmit: function(){
                swal.showLoading();

                options.xaxis.categories = [];
                options.series[0].data = [];
                options.series[1].data = [];
                options.series[2].data = [];
                options.series[3].data = [];
                options.series[4].data = [];
                options.series[5].data = [];
                options.series[6].data = [];
                options.series[7].data = [];

                pieoptions.labels = [];
                pieoptions.series = [];
                totalSum = 0;

                pieoptions2.labels = [];
                pieoptions2.series = [];
                totalSum2 = 0;

                baroptions.xaxis.categories = [];
                baroptions.series[0].data = [];

                baroptions2.xaxis.categories = [];
                baroptions2.series[0].data = [];

                baroptions3.xaxis.categories = [];
                baroptions3.series[0].data = [];
            },
            success: function(data){

                $.each(jQuery.parseJSON(data.string1), function(key, val){
                    pieoptions.labels.push("Yığım məbləği");
                    pieoptions.series.push(round(val.UMUMI_YIGILMIS_MEBLEG, 0));
                    pieoptions.labels.push("Qalıq borc");
                    pieoptions.series.push(round(val.UMUMI_QALIQ_BORC, 0));
                    totalSum = round(val.UMUMI_SATIS_HECMI, 0);
                    baroptions.series[0].data.push(round(val.AYLIQ_YIGIM_HECMI, 0));
                    baroptions.xaxis.categories.push("Aylıq yığım həcmi");
                    baroptions.series[0].data.push(round(val.PROBLEMLI_MUSTERI_CEM, 0));
                    baroptions.xaxis.categories.push("Problemli müştəri qalıq");
                    baroptions3.series[0].data.push(round(val.PROBLEMLI_MUSTERI_SAY, 0));
                    baroptions3.xaxis.categories.push("Problemli müştərilər");
                    baroptions.series[0].data.push(round(val.GECIKDIRMIS_MUSTERI_CEM, 0));
                    baroptions.xaxis.categories.push("Gecikmiş müştəri qalıq");
                    baroptions3.series[0].data.push(round(val.GECIKDIRMIS_MUSTERI_SAY, 0));
                    baroptions3.xaxis.categories.push("Gecikmiş müştərilər");
                });
                piechart.updateOptions(pieoptions);
                barchart.updateOptions(baroptions);
                barchart3.updateOptions(baroptions3);

                $.each(jQuery.parseJSON(data.string8), function(key, val){
                    baroptions2.series[0].data.push(round(val.AYLIQ_YIGIM_HECMI, 0));
                    baroptions2.xaxis.categories.push("Aylıq yığım həcmi");
                    baroptions2.series[0].data.push(round(val.UMUMI_YIGILMIS_MEBLEG, 0));
                    baroptions2.xaxis.categories.push("Ümumi yığılmış məbləğ");
                    baroptions2.series[0].data.push(round(val.UMUMI_QALIQ_BORC, 0));
                    baroptions2.xaxis.categories.push("Ümumi qalıq borc");
                    baroptions2.series[0].data.push(round(val.UMUMI_SATIS_HECMI, 0));
                    baroptions2.xaxis.categories.push("Ümumi satış həcmi");
                });

                barchart2.updateOptions(baroptions2);

                $.each(jQuery.parseJSON(data.string6), function(key, val){
                    pieoptions2.labels.push("Yığım məbləği");
                    pieoptions2.series.push(round(val.LAST_PRICE, 0));
                    pieoptions2.labels.push("Qalıq borc");
                    pieoptions2.series.push(round(val.INVOICES, 0));
                    totalSum2 = round(val.TOTAL_SUM, 0);
                });
                piechart2.updateOptions(pieoptions2);

                $.each(jQuery.parseJSON(data.string2), function(key, val){
                    options.xaxis.categories.push(val.ZAXIS);
                    options.series[0].data.push(round(val.YAXIS, 0));
                    options.series[4].data.push(round(val.MAXIS, 0));
                });

                $.each(jQuery.parseJSON(data.string3), function(key, val){
                    options.series[1].data.push(round(val.YAXIS, 0));
                    options.series[5].data.push(round(val.MAXIS, 0));
                });

                $.each(jQuery.parseJSON(data.string4), function(key, val){
                    options.series[2].data.push(round(val.YAXIS, 0));
                    options.series[6].data.push(round(val.MAXIS, 0));
                });

                $.each(jQuery.parseJSON(data.string5), function(key, val){
                    options.series[3].data.push(round(val.YAXIS, 0));
                    options.series[7].data.push(round(val.MAXIS, 0));
                });
                chart.updateOptions(options);

                swal.close();
            },
            complete: function(){
                swal.close();
            },
            error: function(){
                swal.close();
            }
        });
    });

    function submitFilter(){
        $.ajax({
            url: '/report/api/report-collect',
            type: 'POST',
            dataType: 'json',
            data: $('#filter').serialize(),
            beforeSend: function() {
            },
            success: function(data) {
                console.log(data);
                console.log(jQuery.parseJSON(data.string1));
            },
            complete: function(){
            },
            error: function() {
            }
        })
    }
</script>