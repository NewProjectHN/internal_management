import React from 'react';
import Button from '../Button';
import InputComponent from '../InputComponent';
import {Link} from 'react-router-dom';

export default class TableBody extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            currentValueCheckbox: [],
            checkAll: false
        }
        this.handleDelete = this.handleDelete.bind(this);
        this.handleEdit = this.handleEdit.bind(this);
        // this.handleCheckboxChange = this.handleCheckboxChange.bind(this);
    }
    //khi thay doi state thi goi lai ham
    componentWillReceiveProps(){
        this.setState({
            checkAll: this.props.checkAllProps
        })
    }
    handleDelete(id){
        // alert("delete " + id);
        this.props.valueDelete(id);
    }

    handleEdit(data){
        this.props.valueEdit(data);
    }
    handleCheckboxChange(index,event){
        
        var value = event.target.value;
        console.log(value);
        var dataAll = [];

        for(var i=0;i<this.props.data.rows.length;i++){
           
                for(var i=0;i<this.props.data.rows.length;i++){
                    dataAll.push(""+this.props.data.rows[i][this.props.data.indexId]);
                }
           
        }
        console.log(dataAll);
        for(var i=0;i<dataAll.length;i++){
            if(value==dataAll[i]){
               
                this.props.valueApprove(event,index);
              
            }
        
        }
        
    }
    handleAdd(data){
        this.props.valueAdd(data);
    }

    render(){
        const dataRows = this.props.data.rows;
        const dataColumns = this.props.data.columns;
        const {isCrud,isCheckbox,isSTT,isDetail,isAdd}=this.props;
        const checked =[] ;
        for(var i = 0; i <this.props.valueChecked.length;i++)
            checked.push(this.props.valueChecked[i]);
        const stt=[];
        for(var i=0;i<this.props.data.rows.length;i++)
            stt.push(i+1)
      
        return(
            
            <tbody>
                {dataRows.map(function(row,index){
                    return(
                        <tr key={index} colindex={index+1}>
                        {console.log(checked[index])}
                            {isCheckbox && 
                                <td>
                                    <input type="checkbox" onChange={this.handleCheckboxChange.bind(this,index)} value={row[this.props.data.indexId]} checked={checked[index]}/>
                                </td>
                            }
                            {isSTT && 
                                <td>
                                    {stt[index]}
                                </td>
                            }
                            {dataColumns.map(function(column,index){
                                return <td key={index}>{row[column]}</td>;
                            })}
                            {isDetail &&
                                <td><Link type="button" btn="info" to={"/pages/addemployee/"+ row[this.props.data.indexId]}> Manager Team</Link></td>
                            }
                            {isDetail &&
                                 
                                  <td><Link type="button" btn="info" to={"/pages/allocation/"+ row[this.props.data.indexId]} >View</Link></td>
                            }
                            {isCrud &&
                                  <td><Button type="button" onClick={() => this.handleEdit(row)}>Edit</Button></td>
                            }
                            {isCrud &&
                                <td><Button btn="success"  type="submit" onClick={() => this.handleDelete(row[this.props.data.indexId])}>Delete</Button></td>
                            }
                             {isAdd &&
                                 <td><Button type="button"  btn="success" onClick={() => this.handleAdd(row)}>Create</Button></td>
                            }
                        </tr>);
                }.bind(this))}
            </tbody>
        );
    }
}