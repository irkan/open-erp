function round(value, decimals) {
    return Number(Math.round(value+'e'+decimals)+'e-'+decimals);
}

let buttonCommon = {
    exportOptions: {
        columns: 'th:not(:last-child)',
        format: {
            body: function ( data, row, column, node ) {
                if(data.match(/[<>$]/)){
                    try{
                        data = data.replace(/&nbsp;/gi," ");
                        data = data.replace(/&nbsp;/g, ' ');
                        data = data.replace("<br>", " ");
                        data = $(data).text();
                        data = data.replace(/\s\s+/g, ', ');
                    } catch (e) {
                    }
                }
                return data;
            }
        }
    }
};