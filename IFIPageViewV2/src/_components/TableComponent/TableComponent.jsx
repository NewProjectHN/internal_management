import React from 'react';
import TableHeader from './TableHeaderComponent';
import TableBody from './TableBodyComponent';

export default class TableComponent extends React.Component{
    constructor(props){
        super(props);
        this.state ={valueDelete:0,currentValueCheckbox:[],checkAll: [],sorted:this.props.sorted}
    }
    
    handleDelete(value){
        this.props.onDelete(value);
    }
    valueEdit(value){
        this.props.onEdit(value);
    }
    handleApprove(value){
        this.props.onApprove(value);
    }
    valueAdd(value){
        this.props.onAdd(value);
    }
    handelCheckAll(value){
        console.log(value);
        var dataAll = [];

        for(var i=0;i<this.props.data.rows.length;i++){
            if(value[i]==true){
                for(var i=0;i<this.props.data.rows.length;i++){
                    dataAll.push(""+this.props.data.rows[i][this.props.data.indexId]);
                }
            }else{
                dataAll = [];
            }
        }
        console.log(dataAll);
        this.setState({
            checkAll:value,
            currentValueCheckbox: dataAll
        })
        console.log(dataAll);
        this.props.onApprove(dataAll);

    }

    handleChange(event,index) {
        var checked = this.state.checkAll;
     
        checked[index] = event.target.checked;
      
          
        var value = event.target.value;

        var checkArr = [...this.state.currentValueCheckbox];
        // var checkArr = [];
        // for(var i=0;i<this.state.currentValueCheckbox;i++)
        //     checkArr.push(this.state.currentValueCheckbox[i]);


        const index1 = checkArr.findIndex(d => d === value);

        if(index1 > -1) {
            checkArr = [...checkArr.slice(0, index1), ...checkArr.slice(index1 + 1)]
        } else {
            checkArr.push(value);
        }
        this.setState({currentValueCheckbox: checkArr});
        console.log(checkArr);

        this.props.onApprove(checkArr);

        
        
        //checked[index] = !event.target.checked;
      }
    
    
      handelSort(sort){
        this.props.onSort(sort);
      }
    render(){
        const data = this.props.data;
        const {isCrud,handleDelete,isDetail,isAdd}= this.props;
        return(
            <div className="col-xs-12"> 
            <div className="table-responsive">
                <table className="table table-bordered table-hover">
                    <TableHeader sorted={this.state.sorted} onSort={this.handelSort.bind(this)} data={this.props.data} isCrud={this.props.isCrud} isAdd={this.props.isAdd} isSTT={this.props.isSTT}  isDetail={this.props.isDetail} isCheckbox={this.props.isCheckbox} valueHeader={this.handelCheckAll.bind(this)} valueChecked={this.state.checkAll}/>
                    <TableBody data={this.props.data} isCrud={this.props.isCrud} isAdd={this.props.isAdd} isSTT={this.props.isSTT}  isDetail={this.props.isDetail} isCheckbox={this.props.isCheckbox} valueDelete={this.handleDelete.bind(this)} 
                    valueEdit={this.valueEdit.bind(this)} valueAdd={this.valueAdd.bind(this)} valueApprove={this.handleChange.bind(this)} valueChecked={this.state.checkAll} />
                </table>
            </div>
            </div>
        )
    }
}