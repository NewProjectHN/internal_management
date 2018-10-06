import React from 'react';
import InputComponent from '../InputComponent';

export default class HeaderComponent extends React.Component{

    constructor(props){
        super(props);
        this.state = {checked : [],sorted: this.props.sorted};
        this.colIndex = this.props.colIndex;
        this.idTable = this.props.idTable;
        this.icon = true;
       this.check=true;
        this.handleCheckAll = this.handleCheckAll.bind(this);
    }

    componentDidMount(){
        const checked = [];
        for(var i=0; i< this.props.data.rows.length;i++){
                checked.push(true);
        }
        this.setState({
            checked: checked
        })

        console.log(this.state.checked);
    }
    handleCheckAll(event){
        this.setState({
            checked: this.state.checked.map(function() { 
                return !event.target.checked 
            })
        });
        this.props.valueHeader(this.state.checked);
    }
    
    handleSort(index,columns,event){

        // if(document.getElementsByClassName("theader")[index].getElementsByTagName("span")[0].className==="fa fa-sort-desc"){
        //     document.getElementsByClassName("theader")[index].getElementsByTagName("span")[0].className="fa fa-sort-asc";
        //     console.log(columns)
        //     var sort = {
        //                     "id":columns,
        //                     "desc":false
        //                 }
        //     this.props.onSort(sort);
        // }else if(document.getElementsByClassName("theader")[index].getElementsByTagName("span")[0].className==="fa fa-sort-asc"){
        //     document.getElementsByClassName("theader")[index].getElementsByTagName("span")[0].className="fa fa-sort-desc";
        //     var sort = {
        //         "id":columns,
        //         "desc":true
        //     }
        //     this.props.onSort(sort);
            
        // }
       
        
        if(this.state.sorted.desc === true){
            var sort = {
                                    "id":columns,
                                    "desc":false
                                }
            this.setState({
                sorted:sort,
              
            });
            this.props.onSort(sort);
        }else if(this.state.sorted.desc === false){
            var sort = {
                "id":columns,
                "desc":true
            }
                this.setState({
                sorted:sort,
                
                });
                this.props.onSort(sort);
            }

            // if(document.getElementsByClassName("theader")[index].getElementsByTagName("span")[0].className==="fa fa-sort-desc"){
        //     document.getElementsByClassName("theader")[index].getElementsByTagName("span")[0].className="fa fa-sort-asc";
        //   
        // }else if(document.getElementsByClassName("theader")[index].getElementsByTagName("span")[0].className==="fa fa-sort-asc"){
        //     document.getElementsByClassName("theader")[index].getElementsByTagName("span")[0].className="fa fa-sort-desc";
       
            
        // }
    }
    render(){
        const dataColumns = this.props.data.headerCol;
        const data = this.props.data;
        console.log(data)
        const columns = this.props.data.columns;
        const { check,icon,isDetail } = this.props;
        const sortColumn=this.props.data.sortColumn;
        const {isCrud,isCheckbox,valueChecked,isSTT,isAdd}=this.props;

        var isAllChecked = valueChecked.filter(function(c) {
            return c;
        }).length === this.state.checked.length;
      
        return(
            <thead style={{height:"15px"}}>
                <tr>
                    {isCheckbox &&
                        <th>
                            <input type="checkbox" onChange={this.handleCheckAll}  checked={isAllChecked}/>
                        </th>
                    }
                    {isSTT &&
                        <th>
                            STT
                        </th>
                    }
                    {dataColumns && dataColumns.map(function(column,index){
                        
                        if(!sortColumn.includes(columns[index])){
                            return (<th className="theader" key={index} onClick={this.handleSort.bind(this,index,columns[index])}>
                                        {column}
                                        <span style={{marginLeft:"5px"}} className={(this.state.sorted.id===columns[index] && this.state.sorted.desc === true) ? "fa fa-sort-desc":"fa fa-sort-asc"}></span>
                                    </th>);}
                        // else if(data.sortColumn.id.includes(columns[index])){
                        //     return (<th className="theader" key={index} >
                        //             {column}
                                    
                        //         </th>);
                        // }
                        else if(sortColumn.includes(columns[index])){
                        return (<th className="theader" key={index}>
                                    {column}
                                </th>);
                        }
                    }.bind(this)) }
                    {isDetail &&
                        <th>Manager Team </th>
                    }
                    {isDetail &&
                        <th>View Allocation</th>
                    }
                    {isCrud && 
                        <th>Edit</th>
                    }
                    {isCrud && 
                        <th>Delete</th>
                    }
                    {isAdd &&
                        <th>Created Allocation</th>

                    }
                </tr>
            </thead>
        )
    }
}