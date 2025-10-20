let api = [];
api.push({
    alias: 'api',
    order: '1',
    desc: '门面',
    link: '门面',
    list: []
})
api[0].list.push({
    order: '1',
    methodId: '191894d632252aa20ee2346e3c28ea4e',
    desc: 'index',
});
api.push({
    alias: 'UserRegistrarController',
    order: '2',
    desc: '用户会话控制器',
    link: '用户会话控制器',
    list: []
})
api[1].list.push({
    order: '1',
    methodId: 'f5b9443341192f01f3d50c3cca53367a',
    desc: '注册',
});
api[1].list.push({
    order: '2',
    methodId: 'f33861466d4b3803eaa672228710f721',
    desc: 'root',
});
api[1].list.push({
    order: '3',
    methodId: '3108b855bb769fac933d42256d682ecb',
    desc: '下线',
});
api.push({
    alias: 'UserSessionController',
    order: '3',
    desc: '用户会话控制器',
    link: '用户会话控制器',
    list: []
})
api[2].list.push({
    order: '1',
    methodId: '9945bf016f6bd8157887ca16d1c4a25c',
    desc: '登录',
});
api[2].list.push({
    order: '2',
    methodId: 'e4345ae28aaeafc734ab2674061ba2aa',
    desc: 'current',
});
api.push({
    alias: 'IdCardUserController',
    order: '4',
    desc: '证件用户API接口',
    link: '证件用户api接口',
    list: []
})
api[3].list.push({
    order: '1',
    methodId: 'e291eee97823f6d47c1fa9821e582205',
    desc: 'queryByUserid',
});
api[3].list.push({
    order: '2',
    methodId: '2a276a0567e7440ebc015da34890ae14',
    desc: 'status',
});
api[3].list.push({
    order: '3',
    methodId: '1e642995d8fe00d5c38c51be5e1ec2e2',
    desc: 'authenticate',
});
api.push({
    alias: 'UserController',
    order: '5',
    desc: '用户会话控制器',
    link: '用户会话控制器',
    list: []
})
api[4].list.push({
    order: '1',
    methodId: '598dec50c4a4711e3bd1b3e4451e3f56',
    desc: 'queryByUserid',
});
api[4].list.push({
    order: '2',
    methodId: 'a347c210424145573af30d8387ea2f03',
    desc: 'currentUserPhone',
});
api.push({
    alias: 'ChlUserController',
    order: '6',
    desc: '渠道用户API接口',
    link: '渠道用户api接口',
    list: []
})
api[5].list.push({
    order: '1',
    methodId: '43f7704237e45ad319a88a098b3d290a',
    desc: 'queryByUserid',
});
document.onkeydown = keyDownSearch;
function keyDownSearch(e) {
    const theEvent = e;
    const code = theEvent.keyCode || theEvent.which || theEvent.charCode;
    if (code === 13) {
        const search = document.getElementById('search');
        const searchValue = search.value;
        let searchArr = [];
        for (let i = 0; i < api.length; i++) {
            let apiData = api[i];
            const desc = apiData.desc;
            if (desc.toLocaleLowerCase().indexOf(searchValue) > -1) {
                searchArr.push({
                    order: apiData.order,
                    desc: apiData.desc,
                    link: apiData.link,
                    alias: apiData.alias,
                    list: apiData.list
                });
            } else {
                let methodList = apiData.list || [];
                let methodListTemp = [];
                for (let j = 0; j < methodList.length; j++) {
                    const methodData = methodList[j];
                    const methodDesc = methodData.desc;
                    if (methodDesc.toLocaleLowerCase().indexOf(searchValue) > -1) {
                        methodListTemp.push(methodData);
                        break;
                    }
                }
                if (methodListTemp.length > 0) {
                    const data = {
                        order: apiData.order,
                        desc: apiData.desc,
                        alias: apiData.alias,
                        link: apiData.link,
                        list: methodListTemp
                    };
                    searchArr.push(data);
                }
            }
        }
        let html;
        if (searchValue === '') {
            const liClass = "";
            const display = "display: none";
            html = buildAccordion(api,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        } else {
            const liClass = "open";
            const display = "display: block";
            html = buildAccordion(searchArr,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        }
        const Accordion = function (el, multiple) {
            this.el = el || {};
            this.multiple = multiple || false;
            const links = this.el.find('.dd');
            links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown);
        };
        Accordion.prototype.dropdown = function (e) {
            const $el = e.data.el;
            let $this = $(this), $next = $this.next();
            $next.slideToggle();
            $this.parent().toggleClass('open');
            if (!e.data.multiple) {
                $el.find('.submenu').not($next).slideUp("20").parent().removeClass('open');
            }
        };
        new Accordion($('#accordion'), false);
    }
}

function buildAccordion(apiData, liClass, display) {
    let html = "";
    if (apiData.length > 0) {
         for (let j = 0; j < apiData.length; j++) {
            html += '<li class="'+liClass+'">';
            html += '<a class="dd" href="#' + apiData[j].alias + '">' + apiData[j].order + '.&nbsp;' + apiData[j].desc + '</a>';
            html += '<ul class="sectlevel2" style="'+display+'">';
            let doc = apiData[j].list;
            for (let m = 0; m < doc.length; m++) {
                html += '<li><a href="#' + doc[m].methodId + '">' + apiData[j].order + '.' + doc[m].order + '.&nbsp;' + doc[m].desc + '</a> </li>';
            }
            html += '</ul>';
            html += '</li>';
        }
    }
    return html;
}