<template>
<!--  首页-->
  <div class="w-full h-full z-20">
    <!--      屏幕可视区域视频相关部分-->
    <section class="relative w-screen h-screen">
      <video class="w-screen h-screen object-fill" autoplay muted loop>
        <source src="/首屏视频.mp4"/>
      </video>
      <h1 class="title-description absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 text-center text-white">
        <span class="block text-8xl">南京高淳国际慢城旅游度假区</span>
        <small class="block">Gaochun International Slow Lifestyle Vocational Area</small>
      </h1>

      <p class="side-description absolute left-24 bottom-36 text-white text-5xl">
        <span>相约高淳</span>
        <span>蟹逅慢城</span>
        <span>期待您的到来</span>
      </p>
    </section>


    <!-- 石臼湖介绍部分 -->
    <section class="relative w-screen h-screen flex justify-center items-center">
      <div class="absolute top-0 left-0 w-1/4 h-auto transform  -translate-x-1/2">
        <img src="/石臼湖3.webp" alt="" class="w-full h-full object-cover">
      </div>
      <div class="absolute right-0 bottom-0 w-1/5 h-auto transform translate-y-12">
        <img src="/石臼湖.webp" alt="" class="w-full h-full object-cover">
      </div>
      <div class="flex">
        <div class="flex-1  mx-48 pl-72  space-y-6 tracking-widest">
          <h2 class="text-left text-7xl">
            <strong>石臼湖</strong>
            <br>
            <strong>天空之境</strong>
          </h2>
          <p class="px-18 text-4xl font-medium">
            秋与蓝的碰撞，随性与清冷的交融，让人们将高淳的秋日之美尽收眼底。
            <br>
            若说暖色是秋日的惬意，那么蓝色便是秋日的治愈。
            <br>
            澄澈的天空下，总有一抹亮色唤醒你的灵魂。
          </p>
          <p class="px-18 text-2xl font-thin">
            &nbsp;&nbsp;石臼湖是高淳私藏的一处天空之境，什么滤镜都不需要，因为这里已是仙境般。如果你是一个非常追求浪漫的人，那么一定不能错过“最美的12KM”。坐上这趟“看海”的水上列车，随着车的缓慢行驶，目之所及之处全是宁静蔚蓝的“海”。
          </p>
          <div class="pt-6 w-full h-24 text-white text-center text-3xl border-black border-2 cursor-pointer hover:text-black hover:bg-white transition-all duration-500" style="background-color:#262626">
            <a href="/scenic" class="w-full">
              <span class="">查看更多</span>
              <i class="el-icon-right"></i>
            </a>
          </div>
        </div>
        <div class="w-1/2 h-auto  scale-95">
          <img src="/石臼湖4.webp" alt="" class="w-full object-cover">
        </div>
      </div>
    </section>

    <!-- 螃蟹介绍部分 -->
    <section class="relative w-screen h-screen px-24 py-12 flex" style="background-color:#f6f6f6">
      <div class="absolute top-0 left-1/4 text-3xl text-left transform -translate-y-1/2">
        <h2>
          <strong class="text-9xl font-black" style="font-family:默陌山魂手迹">食</strong>
          在高淳
          <br>
          <span class="text-5xl" style="font-family:默陌山魂手迹">一口吃掉南京秋天</span>
        </h2>
      </div>
      <div class="flex-1 h-full flex flex-wrap content-start mt-48">
        <div style="width:400px;height:300px;">
          <img src="/螃蟹.jfif" alt="" class="object-cover">
        </div>
        <div class="ml-12" style="width:400px;height:300px;">
          <img src="/螃蟹2.jfif" alt="" class="object-cover">
        </div>
        <div style="width:400px;height:300px;">
          <img src="/螃蟹3.jfif" alt="" class="object-cover">
        </div>
        <div class="ml-12" style="width:400px;height:300px;">
          <img src="/螃蟹4.jfif" alt="" class="object-cover">
        </div>
      </div>

      <div class="flex-1 h-full mt-48 text-2xl tracking-widest">
        <p class="">
          被誉为“江南圣地、鱼米之乡”的高淳
          <br>
          是我国螃蟹养殖六大原产地之一
          <br>
          固城湖螃蟹不仅是国家无公害水产品、中国地理标志产品，更拥有全国第一个有机螃蟹证书、第一个螃蟹类中国驰名商标等多个国家级第一的美誉。
          <br>
          靠山吃山，靠水吃水。湖边的水塘，是高淳螃蟹的基地，也是当地百姓生活的地方。五彩斑斓的民居，静静地屹立在一块块水塘之间
        </p>
        <p class="mt-56">
          对于很多蟹粉来说，“吃螃蟹到固城湖”成了隐性共识，每年螃蟹节开幕以后，高淳街头巷尾都会涌现很多寻味而来的游客。
          <br>
          对于高淳人来说，每年9-11月的螃蟹节是一场盛事，也是秋天的华丽开场。
          <br>
          青壳白肚、口感绝佳已经不能完全概括固城湖大闸蟹的优点了，每多一只膏满黄肥、肉质鲜嫩的螃蟹下肚，你就会多爱上高淳一分。
        </p>
        <div class="pt-6 mx-auto mt-32 w-96 h-24 text-white text-center text-3xl border-black border-2 cursor-pointer hover:text-black hover:bg-white transition-all duration-500" style="background-color:#262626">
          <a href="/scenic" class="w-full">
            <span class="">查看更多美食</span>
            <i class="el-icon-right"></i>
          </a>
        </div>
      </div>
    </section>

    <!-- 旅馆介绍部分 -->
    <section class="relative w-screen h-auto px-56 py-12">
      <div class="hotel-panel mx-auto flex flex-wrap justify-center">
        <div class="panel-container mx-12 cursor-pointer" style="width:400px; height:700px;">
          <div class="photo-container  relative overflow-hidden" style="width:400px; height:550px;">
            <img data-src="/素一花园民宿.webp" alt="" class="lozad w-full h-full object-cover"/>
          </div>
          <div class="py-8">
            <strong class="text-4xl">素一花园民宿</strong>
            <p class="hotel-description w-full h-12 mt-4">民宿坐落在风景秀丽的国际慢城中，背靠万亩茶园，春天油菜花盛开，这里有一年一度的金花节，秋天果实累累时，有固城湖螃蟹节，周边有游子山、武家嘴风情谷、森林公园，湿地公园，还有政府修建的慢跑道，宜动宜静。</p>
          </div>
        </div>
        <div class="panel-container mx-12 mt-12 cursor-pointer" style="width:400px; height:700px;">
          <div class="photo-container  relative overflow-hidden" style="width:400px; height:550px;">
            <img data-src="/蘭園民宿.webp" alt="" class="lozad w-full h-full object-cover"/>
          </div>
          <div class="py-8">
            <strong class="text-4xl">蘭園民宿</strong>
            <p class="hotel-description w-full h-12 mt-4">客栈整体以古建筑风格为本， 碧瓦朱甍、琼楼玉宇、百年风雅、精致装修。浪漫与庄严的气质，挑高的门厅和气派的大门，圆形的拱窗和转角的石砌，尽显雍容华贵。亭台楼阁，池馆水榭，映在青松翠柏之中；假山怪石，花坛盆景，藤萝翠竹，点缀其间。</p>
          </div>
        </div>
        <div class="panel-container mx-12 mt-24 cursor-pointer" style="width:400px; height:700px;">
          <div class="photo-container  relative overflow-hidden" style="width:400px; height:550px;">
            <img data-src="/喵姐的花园民宿.webp" alt="" class="lozad w-full h-full object-cover"/>
          </div>
          <div class="py-8">
            <strong class="text-4xl">喵姐的花园民宿</strong>
            <p class="hotel-description w-full h-12 mt-4">在这里，喵姐精心打造小小花园，充满鲜花绿意的庭院助您逃离城市的压力，仿佛来到人间桃花源。柔软舒适的床被，带您进入温柔乡，松弛每一根紧绷的神经。</p>
          </div>
        </div>
        <div class="panel-container mx-12 cursor-pointer" style="width:400px; height:700px;">
          <div class="photo-container  relative overflow-hidden" style="width:400px; height:550px;">
            <img data-src="/正福草堂民宿.webp" alt="" class="lozad w-full h-full object-cover"/>
          </div>
          <div class="py-8">
            <strong class="text-4xl">正福草堂民宿</strong>
            <p class="hotel-description w-full h-12 mt-4">南京正福草堂民宿—— 陆君自辛巳年，营造正福草堂，为民宿，引领中式酒店，今已十有六载。正者，常也;福者，祥瑞也。正福亦五福之意。古有杜甫草堂，庐山草堂，慕其高仪，故从之名曰：正福草堂。 客栈拥房内配有中央空调和液晶电视机，让您感受到更加贴心细致的入住体验。服务人员会提前为您准备好电热水壶和瓶装水以及茶具，以满足您的饮水需求。倘若您在忙碌的一天后想在自己的客房内放松，提供拖鞋、24小时热水和吹风机的客房浴室是不错的选择。</p>
          </div>
        </div>
        <div class="panel-container mx-12 mt-12 cursor-pointer" style="width:400px; height:700px;">
          <div class="photo-container  relative overflow-hidden" style="width:400px; height:550px;">
            <img data-src="/固城湾开元名都度假酒店.webp" alt="" class="lozad w-full h-full object-cover"/>
          </div>
          <div class="py-8">
            <strong class="text-4xl">固城湾开元名都度假酒店</strong>
            <p class="hotel-description w-full h-12 mt-4">酒店毗邻高淳老街，与固城湖一堤之隔，处于高淳高端亲子度假综合体“固城湾”内。酒店拥有3个特色餐厅，3个会议室，2个健身房，配备酒吧、书吧、儿童活动室等设施，酒店融入园林景观，结合民俗特色，意在绘制“持螯把酒论吴越、泛舟戏水笑童颜；临水雅居景开窗，俯仰拾趣固城湾”的画面体验感，打造成南京周边首屈一指的多业态高端亲子度假酒店。
酒店客房绝大多数临水，奉行国潮自信与日式和风并重的美学意识。另外重点打造了部分以亲子休闲为主题的特色客房，从儿童安全、好玩、亲子互动、寓教于乐、休闲五个方面来设计亲子客房的细节。
酒店餐饮定位高端精致餐饮，菜品主打融合徽菜的本地菜，淳餐厅将西餐厅和中餐厅融合，拥有120个餐位，大小包厢7个。酒店另外有蟹主题的特色餐厅，来此餐厅用餐客人可乘船抵达。酒店漫吧为您提供各种酒水及饮料，园内景色尽收眼底。
酒店配有2个健身房和1个室外泳池，多个多功能会议室，其宴会厅子罗厅200平，配备P2高清LED屏，可容纳150人会议和120人中式宴会。
酒店将共享固城湾区内各项娱乐配套项目：白鹿萌宠乐园、春秋阁文化体验博物馆、淳艺工坊、开心麻花剧场、帐篷营地、无动力乐园，水乡游船等。</p>
          </div>
        </div>
        <div class="panel-container mx-12 mt-24 cursor-pointer" style="width:400px; height:700px;">
          <div class="photo-container  relative overflow-hidden" style="width:400px; height:550px;">
            <img data-src="/枕松度假酒店.webp" alt="" class="lozad w-full h-full object-cover"/>
          </div>
          <div class="py-8">
            <strong class="text-4xl">枕松度假酒店</strong>
            <p class="hotel-description w-full h-12 mt-4">酒店座落在山上幽静的松树林中，亦可俯瞰月光湖景，酒店附近地域以美丽的自然风光驰名，在绵延50公里的生态路上，杏花林、竹海、丘陵、河溪、葡萄园以及珍珠一样的湖泊一路穿越。因为长期以来保持自给自足的农业，使得这里的山水保持了江南水乡难得的静谧。</p>
          </div>
        </div>

        <div class="absolute right-0 bottom-0 pt-6 w-96 h-24 text-white text-center text-3xl border-black border-2 cursor-pointer hover:text-black hover:bg-white transition-all duration-500 transform translate-y-1/2" style="background-color:#262626">
          <a href="/scenic" class="w-full">
            <span class="">查看更多名宿酒店</span>
            <i class="el-icon-right"></i>
          </a>
        </div>
      </div>
    </section>

    <!-- 游玩活动资讯介绍部分 -->
    <section class="w-screen h-screen px-96 py-28" style="background-color:#f6f6f6">
      <h2 class="text-9xl font-black" style="font-family:方正字迹-吕建德行楷繁体">精彩活动</h2>
      <br>
      <span class="text-6xl font-bold" style="font-family:方正字迹-吕建德行楷繁体">来到高淳这些活动一定不能错过!</span>
      <div class="mt-28 w-full h-auto flex justify-center">
        <div class="panel-container mx-12" style="width:400px;height:400px">
          <div class="photo-container relative overflow-hidden" style="width:400px;height:275px">
            <div class="photo-mask absolute left-0 top-0 w-full h-full"></div>
            <img data-src="/烛光音乐会.jfif" alt="" class="lozad w-full h-full object-cover">
          </div>
          <div>
            <h3 class="mt-6 text-3xl font-black">
              烛光音乐会
            </h3>
            <p class="mt-6 text-xl font-thin">
              音符悠扬，烛光浮动，宛如星光点点
              <br>
              围坐山野间，灵魂随着弦乐四重奏一起坠入田园乌托邦
              <br>
              等待夜幕完全降临，整片烂漫的花田卸下了白日里的喧嚣，气氛已达高潮
            </p>
          </div>
        </div>
        <div class="panel-container mx-12" style="width:400px;height:400px">
          <div class="photo-container relative overflow-hidden" style="width:400px;height:275px">
            <div class="photo-mask absolute left-0 top-0 w-full h-full"></div>
            <img data-src="/荷香集市.webp" alt="" class="lozad w-full h-full object-cover">
          </div>
          <div>
            <h3 class="mt-6 text-3xl font-black">
              荷香集市
            </h3>
            <p class="mt-6 text-xl font-thin">
              寻一良时，与三五好友，至水慢城千亩荷塘，泛舟湖上，芙蕖妆样美人吟。忘却都市喧嚣，静看庭前花开花谢，漫观天上云卷云舒，在荷香中，穿越一次梦华
              <br>
              当夜幕降临，在繁华热闹的荷香集市里回眸，仿佛穿越千年，梦回大宋东京的市井烟火
            </p>
          </div>
        </div>
        <div class="panel-container mx-12" style="width:400px;height:400px">
          <div class="photo-container relative overflow-hidden" style="width:400px;height:275px">
            <div class="photo-mask absolute left-0 top-0 w-full h-full"></div>
            <img data-src="/帐篷露营.webp" alt="" class="lozad w-full h-full object-cover">
          </div>
          <div>
            <h3 class="mt-6 text-3xl font-black">
              帐篷露营
            </h3>
            <p class="mt-6 text-xl font-thin">
              遇见水慢城百荷园才知道
              <br>
              诗和远方其实并不遥远
              <br>
              白天，在露营地，享受惬意的帐篷露营
              <br>
              夜晚，在荷塘边，感受夏日夜晚的浪漫
            </p>
          </div>
        </div>
        <div class="panel-container mx-12" style="width:400px;height:400px">
          <div class="photo-container relative overflow-hidden" style="width:400px;height:275px">
            <div class="photo-mask absolute left-0 top-0 w-full h-full"></div>
            <img data-src="/非遗盛宴.jfif" alt="" class="lozad w-full h-full object-cover">
          </div>
          <div>
            <h3 class="mt-6 text-3xl font-black">
              非遗盛宴
            </h3>
            <p class="mt-6 text-xl font-thin">
              每年我国文化和自然遗产日,地处吴头楚尾历史文化底蕴深厚、非物质文化遗产层出不穷的高淳隆重举行文化遗产宣传展示活动
            </p>
          </div>
        </div>
      </div>
      <div class="mx-auto mt-12 pt-6 w-96 h-24 text-white text-center text-3xl border-black border-2 cursor-pointer hover:text-black hover:bg-white transition-all duration-500 transform translate-y-1/2" style="background-color:#262626">
        <a href="/scenic" class="w-full">
          <span class="">查看更多活动咨询</span>
          <i class="el-icon-right"></i>
        </a>
      </div>
    </section>

        <!-- 友链部分 -->
    <section class="w-screen h-auto px-24 py-24" style="background-color:wheat">
      <h2 class="text-center text-7xl" style="font-family:字心坊初恋物语">友情连接</h2>
      <div class="mt-24 w-full flex justify-center">
        <div class="mx-16 overflow-hidden" style="width:200px;height:200px">
          <a href="http://www.yxcittaslow.com/#/" class="block mx-auto" style="width:200px;height:175px">
            <img src="/桠溪国际慢城.webp" alt="" class="w-full h-full object-cover transform transition duration-500 hover:scale-105">
          </a>
        </div>
        <div class="mx-16 overflow-hidden" style="width:200px;height:200px">
          <a href="http://www.njgclj.cn/" class="block mx-auto" style="width:175px;height:200px">
            <img src="/高淳老街.webp" alt="" class="w-full h-full object-cover transform transition duration-500 hover:scale-105">
          </a>
        </div>
        <div class="mx-16 overflow-hidden" style="width:200px;height:200px">
          <a href="http://www.chinacittaslow.com/index.php?c=article&id=794" class="block mx-auto w-full h-full transform transition duration-500 hover:scale-105">
            <img src="/水慢城.webp" alt="" class="w-full h-full object-cover">
          </a>
        </div>
        <div class="mx-16 overflow-hidden" style="width:200px;height:200px">
          <a href="http://www.chinacittaslow.com/index.php?c=article&id=1569" class="block mx-auto w-full h-full transform transition duration-500 hover:scale-105">
            <img src="/游子山.webp" alt="" class="w-full h-full object-cover">
          </a>
        </div>
      </div>
    </section>

    <!-- 游记与反馈(进入游记和反馈的页面) -->
    <section class="w-screen h-96 py-24 flex justify-center items-center">
      <div class="plan-faq-panel w-auto h-full pr-56 border-r" style="border-color:#111">
        <a href="/route" class="block text-center space-y-8">
          <div class="text-7xl font-normal" style="color: #111">PLAN</div>
          <p class="text-xl" style="line-height:24px">
            假期和小伙伴们一起旅行
            <br>
            点击进入推荐线路
          </p>
          <div>
            <span class="relative">VIEW MORE</span>
          </div>
        </a>
      </div>
      <div class="plan-faq-panel w-auto h-full pl-56">
        <a href="/feedback" class="block text-center space-y-8">
          <div class="text-7xl font-normal" style="color: #111">FAQ</div>
          <p class="text-xl" style="line-height:24px">
            如果您有什么疑问或建议的话
            <br>
            欢迎随时发表
          </p>
          <div>
            <span class="relative">VIEW MORE</span>
          </div>
        </a>
      </div>
    </section>
  </div>
</template>

<script>
import lozad from 'lozad'  // 这个库的懒加载是自动加载所有配置了的内容，不是滚动到可视区域才加载

export default {
  name: 'IndexPage',
  components: {

  },
  data () {
    return {

    }
  },
  mounted() {
    const observer = lozad(); // lazy loads elements with default selector as '.lozad'
    observer.observe();
  },
  methods: {

  },

  async asyncData(context) {
    // let res = await context.app.$axios.get("/firstScreen/info")
    // return res.data.items
  }
}
</script>

<style scoped>

/* 视频左下角文字 */
.side-description{
  writing-mode: vertical-rl;
  font-family: 方正字迹-吕建德行楷繁体;
}
.side-description span{
  position: relative;
  display: block;
  padding: 5px 12px;
}
.side-description span::after{
  content: "";
  position: absolute;
  top: 0;
  right: 0;
  width: 2px;
  height: 100%;
  background-color: azure;
}


/* 扩展面板 */
.panel{
  flex: 1;
  border-radius: 5rem;
  background-color: greenyellow;
  transition: all 1s ease-in-out;
}
.panel-hover{
  flex: 10;
}


/*旅馆部分 */
.panel-container img{
  position: relative;
  transition: transform 1s ease-in-out;
}
.panel-container:hover img{
  transform: scale(1.05);
}
.panel-container:hover .photo-container::before{
  position: absolute;
  content: "";
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(255, 255, 255, 0.2);
  z-index: 100 ;
}
.hotel-description{
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}


/* 计划与问答面板 */
.plan-faq-panel span::after{
  content: "";
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 1px;
  background-color: #111;
  transition: all 0.5s ease-in-out;
}

.plan-faq-panel:hover span::after{
  width: 0;
}


/* 图片遮罩动画 */
.photo-mask{
  animation-name: move;
  animation-duration: 1s;
  animation-delay: 1s;
  animation-fill-mode: forwards;
  z-index: 200;
}
@keyframes move{
  0%{
    background-color: #111;
    transform: translateX(-100%);
  }
  20%{
    background-color: #111;
    transform: translateX(0);
  }
  70%{
    background-color: #111;
    transform: translateX(0);
  }
  100%{
    background-color: #111;
    transform: translateX(100%);
  }
}
</style>
