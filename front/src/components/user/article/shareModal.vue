<template>
    <div class="container-share">
        <div class="modal-share ">
            <button class="btn-kakao">
            <svg 
                id="Bold" 
                enable-background="new 0 0 24 24" 
                height="24" 
                viewBox="0 0 24 24" 
                width="24" 
                xmlns="http://www.w3.org/2000/svg"
            >
                <path d="m9.462 9.306-.692 1.951h1.383z"/>
                <path d="m12 1c-6.627 0-12 4.208-12 9.399 0 3.356 2.246 6.301 5.625 7.963-1.299 4.45-1.333 4.47-1.113 4.599.276.161.634-.005 
                    5.357-3.311.692.097 1.404.148 2.131.148 6.627 0 12-4.208 12-9.399s-5.373-9.399-12-9.399zm-5.942 12.023c0 
                    .362-.311.657-.692.657s-.692-.295-.692-.657v-4.086h-1.08c-.375 0-.679-.302-.679-.673s.303-.674.678-.674h3.545c.375 0 
                    .679.302.679.673s-.305.674-.679.674h-1.08zm5.378.648c-.72 0-.587-.565-.919-1.195h-2.111c-.329.625-.2
                    1.195-.919 1.195-.693.001-.815-.421-.604-1.071l1.656-4.33c.117-.33.471-.669.922-.679.452.01.807.349.923.679 1.093 3.39 2.654 
                    5.402 1.052 5.401zm3.939-.092h-2.221c-1.159 0-.454-1.565-.663-5.301 0-.379.317-.688.707-.688s.707.308.707.688v4.04h1.471c.366 0 
                    .663.283.663.63-.001.348-.298.631-.664.631zm5.419-.518c-.025.181-.122.344-.269.454-.955.721-1.661-1.381-2.593-2.271l-.24.239v1.5c0 
                    .38-.31.688-.693.688-.382 0-.692-.308-.692-.688v-4.705c0-.379.31-.688.692-.688s.692.308.692.688v1.478c1.277-.958 1.985-2.67 
                    2.792-1.869.792.786-.848 1.474-1.527 2.422 1.604 2.212 1.909 2.267 1.838 2.752z"
                />
            </svg>
            </button>
            <button class="btn-facebook">
                <svg 
                    version="1.1" 
                    id="Capa_1" 
                    xmlns="http://www.w3.org/2000/svg" 
                    xmlns:xlink="http://www.w3.org/1999/xlink" 
                    width="24px"
                    height="24px"
                    viewBox="0 0 455.73 455.73" 
                    style="enable-background:new 0 0 455.73 455.73;" 
                    xml:space="preserve">
                    <path style="fill:#3A559F;" d="M0,0v455.73h242.704V279.691h-59.33v-71.864h59.33v-60.353c0-43.893,35.582-79.475,79.475-79.475
                        h62.025v64.622h-44.382c-13.947,0-25.254,11.307-25.254,25.254v49.953h68.521l-9.47,71.864h-59.051V455.73H455.73V0H0z"/>
                </svg>
            </button>
            <button class="btn-twitter">
                <svg 
                    width="24"
                    height="24"
                    version="1.1" id="Capa_1" 
                    xmlns="http://www.w3.org/2000/svg" 
                    xmlns:xlink="http://www.w3.org/1999/xlink" 
                    viewBox="0 0 455.731 455.731" 
                    style="enable-background:new 0 0 455.731 455.731;" 
                    xml:space="preserve">
                <g>
                    <rect x="0" y="0" style="fill:#50ABF1;" width="455.731" height="455.731"/>
                    <path style="fill:#FFFFFF;" d="M60.377,337.822c30.33,19.236,66.308,30.368,104.875,30.368c108.349,0,196.18-87.841,196.18-196.18
                        c0-2.705-0.057-5.39-0.161-8.067c3.919-3.084,28.157-22.511,34.098-35c0,0-19.683,8.18-38.947,10.107
                        c-0.038,0-0.085,0.009-0.123,0.009c0,0,0.038-0.019,0.104-0.066c1.775-1.186,26.591-18.079,29.951-38.207
                        c0,0-13.922,7.431-33.415,13.932c-3.227,1.072-6.605,2.126-10.088,3.103c-12.565-13.41-30.425-21.78-50.25-21.78
                        c-38.027,0-68.841,30.805-68.841,68.803c0,5.362,0.617,10.581,1.784,15.592c-5.314-0.218-86.237-4.755-141.289-71.423
                        c0,0-32.902,44.917,19.607,91.105c0,0-15.962-0.636-29.733-8.864c0,0-5.058,54.416,54.407,68.329c0,0-11.701,4.432-30.368,1.272
                        c0,0,10.439,43.968,63.271,48.077c0,0-41.777,37.74-101.081,28.885L60.377,337.822z"/>
                </g>
                </svg>
            </button>
        </div>
    </div>
</template>

<script>
export default {
     methods: {
        closeNotification(){
            document.querySelector(".container-share").classList.add("hide");
        },
        sendSns(sns, url, txt){
            var o;
            var _url = encodeURIComponent(url);
            var _txt = encodeURIComponent(txt);
            var _br  = encodeURIComponent('\r\n');
 
            switch(sns){
                case 'facebook':
                    o = {
                        method:'popup',
                        url:'http://www.facebook.com/sharer/sharer.php?u=' + _url
                    };
                    break;
        
                case 'twitter':
                    o = {
                        method:'popup',
                        url:'http://twitter.com/intent/tweet?text=' + _txt + '&url=' + _url
                    };
                    break;
        
                case 'kakaotalk':
                    o = {
                        method:'web2app',
                        param:'sendurl?msg=' + _txt + '&url=' + _url + '&type=link&apiver=2.0.1&appver=2.0&appid=dev.epiloum.net&appname=' + encodeURIComponent('Epiloum 개발노트'),
                        a_store:'itms-apps://itunes.apple.com/app/id362057947?mt=8',
                        g_store:'market://details?id=com.kakao.talk',
                        a_proto:'kakaolink://',
                        g_proto:'scheme=kakaolink;package=com.kakao.talk'
                    };
                    break;
        
                case 'band':
                    o = {
                        method:'web2app',
                        param:'create/post?text=' + _txt + _br + _url,
                        a_store:'itms-apps://itunes.apple.com/app/id542613198?mt=8',
                        g_store:'market://details?id=com.nhn.android.band',
                        a_proto:'bandapp://',
                        g_proto:'scheme=bandapp;package=com.nhn.android.band'
                    };
                    break;
        
                default:
                    alert('지원하지 않는 SNS입니다.');
                    return false;
            }
            window.open(o.url);
        }
    }
}
</script>

<style scoped lang="scss">
    .container-share {
        position: relative;
        top: 50px;
        left: -160px;
        width: 0;
        height: 0;
        .modal-share {
            display: flex;
            justify-content: center;
            align-items: center;
            position: absolute;
            background-color: white;
            width: 150px;
            border: 3px solid #c0c0c0;
        }
    }
</style>