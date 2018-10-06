import { mypageConstants } from '../_constants/mypage.constants.js';

export const mypageActions={
    openPopup,
    closePopup
};

function openPopup(){
    return {type: mypageConstants.OPEN_POPUP };
}
function closePopup(){
    return {type: mypageConstants.CLOSE_POPUP};
}