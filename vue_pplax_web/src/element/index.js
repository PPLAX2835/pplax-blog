//按需引入element组件
import {
    Autocomplete,
    Dialog,
    Dropdown,
    DropdownMenu,
    DropdownItem,
    Input,
    Radio,
    RadioGroup,
    RadioButton,
    Select,
    Option,
    OptionGroup,
    Button,
    ButtonGroup,
    Popover,
    Tooltip,
    Form,
    FormItem,
    Tabs,
    TabPane,
    Tag,
    Icon,
    Row,
    Col,
    Upload,
    Badge,
    Card,
    Carousel,
    CarouselItem,
    Divider,
    Image,
    Drawer,
    Timeline,
    TimelineItem,
    Avatar,
    InfiniteScroll,
    Empty,
    MessageBox
} from 'element-ui';

//封装组件
const element = {
    install: function (Vue) {
        Vue.use(Autocomplete);
        Vue.use(Dialog);
        Vue.use(Dropdown);
        Vue.use(DropdownMenu);
        Vue.use(DropdownItem);
        Vue.use(Input);
        Vue.use(Radio);
        Vue.use(RadioGroup);
        Vue.use(RadioButton);
        Vue.use(Select);
        Vue.use(Option);
        Vue.use(OptionGroup);
        Vue.use(Button);
        Vue.use(ButtonGroup);
        Vue.use(Popover);
        Vue.use(Tooltip);
        Vue.use(Form);
        Vue.use(FormItem);
        Vue.use(Tabs);
        Vue.use(TabPane);
        Vue.use(Tag);
        Vue.use(Icon);
        Vue.use(Row);
        Vue.use(Col);
        Vue.use(Upload);
        Vue.use(Badge);
        Vue.use(Card);
        Vue.use(Carousel);
        Vue.use(CarouselItem);
        Vue.use(Divider);
        Vue.use(Image);
        Vue.use(Drawer);
        Vue.use(Timeline);
        Vue.use(TimelineItem);
        Vue.use(Avatar);
        Vue.use(InfiniteScroll);
        Vue.use(Empty);
        Vue.prototype.$confirm = MessageBox.confirm
    }
}
//抛出整体按需引入的组件
export default element
