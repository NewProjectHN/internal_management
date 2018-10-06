import {employeeConstants} from '../_constants/employee.constants';

export function employees(state = {},action){
    switch(action.type){
        case employeeConstants.GETALL_REQUEST_EMPLOYEE_IN_PROJECT:
            return {
                loading: true,
                loadTwo: false
            };
        case employeeConstants.GETALL_FAILURE_EMPLOYEE_IN_PROJECT:
            return{
                loading: false,
                error: action.error
            }
        case employeeConstants.GETALL_SUCCESS_EMPLOYEE_NOTIN_PROJECT:
            return{
                loading: false,
                // employeeListIn: action.employeeNotInBean.data1,
                employeeListNotIn: action.employeeNotInBean.data1.content,
                employeeList: action.employeeNotInBean.data.content,
                pages: action.employeeNotInBean.pages
            }
        case employeeConstants.GETALL_SUCCESS_EMPLOYEE_IN_PROJECT:
            return{
                loading:false,
                employeeListInProject: action.employeeBean.data,
                pages:action.employeeBean.pages
            }
        //Get all emp:Nguyen
        case employeeConstants.GETALL_REQUEST:
            return {
                loading: true
            };
        case employeeConstants.GETALL_SUCCESS:
            return{
                loading:false,
                empList:action.empList.data
            }
        case employeeConstants.GETALL_FAILURE:
            return{
                loading:false
            };
        case employeeConstants.DELETE_EMPLOYEE_REQUEST:
            console.log(action);
            console.log(state);
            return{
                loading:true
            };
        case employeeConstants.DELETE_EMPLOYEE_SUCCESS:
            console.log(action);
            console.log(state);
            return{
                loading:false
            };
        case employeeConstants.DELETE_EMPLOYEE_ERROR:
            console.log(action);
            console.log(state);
            return{
                loading:false
            };
        default:
            return state;
    }

}