import {overtimeConstants} from '../_constants/overtime.constants.js';
import { overtimeManagerConstants } from '../_constants/overtimeManager.constants.js';

export function overtimes(state = {},action){
    switch(action.type){

        //My Overtime
        case overtimeConstants.GETALL_REQUEST:
            return {
                loading: true
            };
        case overtimeConstants.GETALL_SUCCESS:
            return{
                loading: false,
                overtimeList: action.overtimeBean.data,
                pages: action.overtimeBean.pages
            }
        case overtimeConstants.GETALL_FAILURE:
            return{
                loading: false,
                error: action.error
            }
        case overtimeConstants.SEARCH_OVERTIME_REQUEST:
            return{
                loading: true
            };
        case overtimeConstants.SEARCH_OVERTIME_SUCCESS:
            return{
                loading: false,
                searchList: action.overtimeSearch.data,
                searchPages: action.overtimeSearch.pages
        }
        case overtimeConstants.SEARCH_OVERTIME_FAILURE:
            return{
                loading: false,
                error: action.error
            }

        case overtimeConstants.SAVE_OVERTIME_SUCCESS:
            let overtimeOldList=state.overtimeList;
            const overtimeNewList=[];
            overtimeNewList.push(action.overtime.data);
            if(overtimeOldList!==undefined){
            for(var i=0;i<overtimeOldList.length;i++)
                overtimeNewList.push(overtimeOldList[i]);
            }
            return{
                overtimeList: overtimeNewList,
                pages: state.pages
            };

        case overtimeConstants.EDIT_OVERTIME_SUCCESS:
            let updateList = state.overtimeList;
            const update = [];
            let searchList2=state.searchList;
            let searchPages2=state.searchPages;
            if(searchList2===undefined){
                if(updateList){
                    for(var i=0;i<updateList.length;i++){
                        
                        if(updateList[i].overtime_id == action.overtime.data.overtime_id)
                            update.push(action.overtime.data);
                        else
                            update.push(updateList[i]);
                        
                    }
                }
                return{
                    overtimeList: update,
                    pages: state.pages
                }
            }
            else {
                if(searchList2){
                    for(var i=0;i<searchList2.length;i++){
                        
                        if(searchList2[i].overtime_id == action.overtime.data.overtime_id)
                            update.push(action.overtime.data);
                        else
                            update.push(searchList2[i]);
                        
                    }
                }
                return{
                    overtimeList: update,
                    pages: searchPages2
                }
            }
        case overtimeConstants.DELETE_OVERTIME_SUCCESS:
            let overtimeList = state.overtimeList;
            let overtimeDelete = [];
            let searchList=state.searchList;
            let searchPages=state.searchPages;
            if(searchList===undefined){
                for (var i = 0 ;i < overtimeList.length;i++) {
                  if(overtimeList[i].overtime_id != action.id){
                    overtimeDelete.push(overtimeList[i]);
                  }
                }
            }
            else
            {for (var i = 0 ;i < searchList.length;i++) {
                if(searchList[i].overtime_id != action.id){
                    overtimeDelete.push(searchList[i]);
                }
              }
            }
               // state.leaveList = leaveDelete;
               if(searchPages===undefined){
                return {
                    overtimeList: overtimeDelete,
                    pages: state.pages
                };
            }
            else return {
                overtimeList: overtimeDelete,
                    pages: searchPages
            }
            //My Approve overtime
        case overtimeManagerConstants.GETALL_REQUEST:
            return {
                loading: true
            };
        case overtimeManagerConstants.GETALL_SUCCESS:
            return{
                loading: false,
                overtimeEmp: action.overtimeEmp.data,
                pages: action.overtimeEmp.pages
            }
        case overtimeManagerConstants.GETALL_FAILURE:
            return{
                loading: false,
                error: action.error
            }
            case overtimeManagerConstants.GETALL_APPROVED_REQUEST:
            return{
                loading:true
            }
        case overtimeManagerConstants.GETALL_APPROVED_SUCCESS:
            return{
                loading:false,
                overtimeEmp:action.overtimeEmp.data,
                pages:action.overtimeEmp.pages
            }
        case overtimeManagerConstants.GETALL_APPROVED_FAILURE:
            return{
                loading:false,
                error:action.error
            }
            case overtimeManagerConstants.GETALL_DISAPPROVED_REQUEST:
            return{
                loading:true
            }
        case overtimeManagerConstants.GETALL_DISAPPROVED_SUCCESS:
            return{
                loading:false,
                overtimeEmp:action.overtimeEmp.data,
                pages:action.overtimeEmp.pages
            }
        case overtimeManagerConstants.GETALL_DISAPPROVED_FAILURE:
            return{
                loading:false,
                error:action.error
            }


        case overtimeManagerConstants.APPROVE_REQUEST:
            return{
                loading:true
            };
        case overtimeManagerConstants.APPROVE_SUCCESS:
            let overtimeEmp = state.overtimeEmp;
            let overtimeApp = [];
            let searchEmpList=state.searchEmpList;
            let searchEmpPages=state.searchEmpPages;
            if(searchEmpList===undefined){
                for(var i= 0 ; i < overtimeEmp.length ; i++){
                    if(overtimeEmp[i].overtime_id != action.overtimeEmpApprove.data.overtime_id){
                        overtimeApp.push(overtimeEmp[i]);
                    }
                }
                return {
                    pages:state.pages,
                    overtimeEmp: overtimeApp
                };
            }
            else {
                for(var i= 0 ; i < searchEmpList.length ; i++){
                    if(searchEmpList[i].overtime_id != action.overtimeEmpApprove.data.overtime_id){
                        overtimeApp.push(searchEmpList[i]);
                    }
                }
                return {
                    pages: searchEmpPages,
                    overtimeEmp: overtimeApp
                };
            }
        case overtimeManagerConstants.UNAPPROVE_REQUEST:
            return{
                loading:true
            };
        case overtimeManagerConstants.UNAPPROVE_SUCCESS:
            let overtimeEmpUn = state.overtimeEmp;
            let overtimeAppUn = [];
            let searchEmpList2=state.searchEmpList;
            let searchEmpPages2=state.searchEmpPages;
            if(searchEmpList2===undefined){
                for(var i= 0 ; i < overtimeEmpUn.length ; i++){
                    if(overtimeEmpUn[i].overtime_id != action.overtimeEmpUnApprove.data.overtime_id){
                        overtimeAppUn.push(overtimeEmpUn[i]);
                    }
                }
                return {
                    pages:state.pages,
                    overtimeEmp: overtimeAppUn
                };
            }
            else{
                for(var i= 0 ; i < searchEmpList2.length ; i++){
                    if(searchEmpList2[i].overtime_id != action.overtimeEmpUnApprove.data.overtime_id){
                        overtimeAppUn.push(searchEmpList2[i]);
                    }
                }
                return {
                    pages:searchEmpPages2,
                    overtimeEmp: overtimeAppUn
                };
            }
        case overtimeManagerConstants.SEARCH_OVERTIME_REQUEST:
            return{
                loading: true
            };
        case overtimeManagerConstants.SEARCH_OVERTIME_SUCCESS:
            return{
                loading: false,
                searchEmpList: action.empSearch.data,
                searchEmpPages: action.empSearch.pages
        }
        case overtimeManagerConstants.SEARCH_OVERTIME_FAILURE:
            return{
                loading: false,
                error: action.error
            }


        default:
            return state;
    }

}