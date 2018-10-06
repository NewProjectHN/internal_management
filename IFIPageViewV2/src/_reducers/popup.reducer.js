import { mypageConstants } from '../_constants/mypage.constants.js';

export function popup(state = {}, action){
    switch(action.type){
        case mypageConstants.OPEN_POPUP:
            return{
                isOpen:true
            };
        case mypageConstants.CLOSE_POPUP:
            return{
                isOpen:false
            };
        default:
            return state
    }
}