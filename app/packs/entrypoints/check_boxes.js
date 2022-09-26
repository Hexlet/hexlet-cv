const axios = require('axios');
const checkboxes = ["work_course", "resume", "letter", "open_source", "code_battle", "hexlet", "github", "interview"]

const prepare_data = () => {
  result = []
  checkboxes.map((el) => {
    if (document.getElementById(`user_profile_checkboxes_${el}`).checked) {
      result.push(el)
    } 
  })
  return result;
} 

checkboxes.map((el) => {
  document.getElementById(`user_profile_checkboxes_${el}`).addEventListener('change', function (e) {
    axios({
      method: 'PATCH',
      url: '/account/profile/check_box',
      data: { user: { profile_checkboxes: prepare_data()} }
    }).then(() => document.getElementById("checkbox").innerHTML = prepare_data().length)
  });
})
