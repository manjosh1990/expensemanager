const headers = {
  Accept: "application/json",
  "Content-type": "application/json",
};

const joinUrl = (baseUrl, url)=>{
  return `${baseUrl}/${url}`
}
class Service {
  constructor() {
    this.domain = "http://localhost:10443/xpense/service/v1/dashboard";
  }

  request(url,method="POST",data=null) {
    url = joinUrl(this.domain, url);
    const options ={
      headers,
      method,
    }
    if(data){
      options.body = JSON.stringify({...data});
    }

    return fetch(url, options);
  }

  post() {}

  get(url,id) {
    const method ="GET";
    if(id){
      url=`${url}/${id}`;
    }
    return this.request(url,method).then(res => res.json());
  }
  delete() {}
  put() {}
  getAll() {}
}

export default Service;
