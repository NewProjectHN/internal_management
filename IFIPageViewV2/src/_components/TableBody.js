import React, { Component } from 'react';




class TableBody extends Component {
  	render() {
    	return (
	    	<tr className="even pointer">
	           	<td className="a-center ">
	              	<input type="checkbox" className="flat" name="check"/>
	           	</td>
	           	<td className=" ">{this.props.Invoice}</td>
	      		<td className=" ">{this.props.InvoiceDate}</td>
	           	<td className=" ">{this.props.BilltoName}</td>
	           	<td className=" ">{this.props.Status}</td>
	           
	           	<td className="last">View
	           	</td>
	        </tr>  
    	);
  	}
}

export default TableBody;