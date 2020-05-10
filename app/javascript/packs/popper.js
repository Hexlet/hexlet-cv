document.addEventListener("DOMContentLoaded", function(){
  const myWhiteList = $.fn.tooltip.Constructor.Default.whiteList;

  myWhiteList.a.push('data-click', 'data-click-input');
  myWhiteList.input = ['type', 'value', 'readonly'];

  $('[data-toggle="popover"]').popover({
    container: 'body'
  })

  $('[data-toggle="popover"]').on('shown.bs.popover', function () {
    $('[data-click="copy"]').on('click', (event) => {
      event.preventDefault();

      const input = document.getElementById(event.target.dataset.clickInput);
      input.select();
      document.execCommand('copy');
    })


  })
});

