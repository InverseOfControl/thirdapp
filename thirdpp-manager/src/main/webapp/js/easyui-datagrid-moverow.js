var DatagridMoveRow = (function($){
 
    function DatagridMoveRow(gridTarget){
        this.el = gridTarget;
        this.$el = $(this.el);
        this.rowIndex = -1;
        this.selectRow = null;
        this.rowsCount = this.$el.datagrid('getData').rows.length;
        return this;
    }
 
    DatagridMoveRow.prototype = {
        getRowindex: function(){
        	var selectrow= this.$el.datagrid('getSelected');  
            var selectRowIndex = this.$el.datagrid('getRowIndex', selectrow);  
            if(selectRowIndex == -1){
                this.rowIndex = 0 ;
            }else{
                this.rowIndex = selectRowIndex;
                this.selectRow = selectrow;
            }
        },
        moveUp: function(){
 
            this.getRowindex();
 
            if(this.rowIndex ==0){
            	$.messager.alert('提示', '顶行无法上移!', 'warning');  
                return false;
            }
 
            this.$el.datagrid('deleteRow', this.rowIndex);//删除一行  
            this.rowIndex--;  
            this.$el.datagrid('insertRow', {  
                index:this.rowIndex,  
                row:this.selectRow  
            });  
            this.$el.datagrid('selectRow', this.rowIndex);  
 
            return false;
        },
        moveDown: function (){
            this.getRowindex();
            if(this.rowIndex == this.rowsCount -1 ){
            	$.messager.alert('提示', '底行无法下移!', 'warning'); 
                return false;
            }
            this.$el.datagrid('deleteRow', this.rowIndex);//删除一行  
            this.rowIndex++;  
            this.$el.datagrid('insertRow', {  
                index:this.rowIndex,  
                row:this.selectRow  
            });  
            this.$el.datagrid('selectRow', this.rowIndex);  
        }
    }
 
    return DatagridMoveRow;
 
})(jQuery);