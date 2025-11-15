document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('paymentForm');
  const els = {
    name: document.getElementById('cardname'),
    number: document.getElementById('cardnumber'),
    exp: document.getElementById('exp'),
    terms: document.getElementById('terms')
  };


  els.number.addEventListener('input', e => {
    let v = e.target.value.replace(/\D/g, '').slice(0, 16);
    e.target.value = v ? v.match(/.{1,4}/g).join(' ') : '';
  });


  els.exp.addEventListener('input', e => {
    let v = e.target.value.replace(/\D/g, '').slice(0, 6);
    if (v.length >= 3) v = v.slice(0, 2) + '/' + v.slice(2, 6);
    e.target.value = v;
  });


  const error = field => field.parentElement.classList.add('error');
  const clearErrors = () => document.querySelectorAll('.field').forEach(f => f.classList.remove('error'));


  form.addEventListener('submit', e => {
    e.preventDefault();
    clearErrors();
    let ok = true;

  
    if (!/^[A-Za-zÀ-ỹ\s]+$/.test(els.name.value.trim())) {
      error(els.name); ok = false;
    }

  
    if (els.number.value.replace(/\s/g, '').length !== 16) {
      error(els.number); ok = false;
    }

 
    const exp = els.exp.value.trim();
    if (!/^(0[1-9]|1[0-2])\/\d{4}$/.test(exp)) {
      error(els.exp); ok = false;
    } else {
      const [month, year] = exp.split('/').map(Number);
      const now = new Date();
      const expiry = new Date(year, month); 

      if (expiry <= now) {
        error(els.exp); ok = false;
        alert('Thanh toán thất bại!');
      }
    }


    if (!els.terms.checked) {
      alert('Vui lòng đồng ý với điều khoản dịch vụ!');
      ok = false;
    }

    if (ok) {
  alert('Thanh toán thành công!');


  const btn = form.querySelector('.btn-pay');


  if (btn && btn.onclick) {
    setTimeout(() => btn.onclick(), 600);
  }
}

  });
});