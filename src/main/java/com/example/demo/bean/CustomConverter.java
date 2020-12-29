package com.example.demo.bean;

import net.sf.cglib.beans.BeanCopier;


/**
 * @param <S> 资源类
 * @param <T> 目标类
 * @ClassName DTOConverter
 * @Description DTO转化器
 * @PackageName com.tfswx.sms.dto.base.DTOConverter
 * @Author 杨登柳
 * @Date 2020/12/26 15:16
 **/
public class CustomConverter<S, T> {

    private T t;

    /**
     * 通过dto类名,利用反射创建dto类
     *
     * @param c
     */
    private CustomConverter(Class<T> c) {
        try {
            this.t = c.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CustomConverter() {
    }

    /**
     * 传入资源对象与目标对象,进行对象属性的复制
     * <p>
     * <p> 继承</p>
     * UserDTO dto = new UserDTO();
     * dto.convert(user, dto);
     * </p>
     * <p>
     * <p>不继承</p>
     * UserDTO dto = new UserDTO();
     * new CustomConverter<User,UserDTO>().convert(user,dto);
     * </p>
     *
     * @param source 资源对象
     * @param target 目标对象
     */
    public void convert(S source, T target) {
        if (source == null){
            return;
        }
        BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), false);
        copier.copy(source, target, null);
    }

    /**
     * 传入资源对象,返回目标对象
     * <p>必须继承</p>
     * <p>
     * UserDTO dto = new UserDTO().convertByInherit(user);
     * </p>
     *
     * @param source 资源对象
     * @return 目标对象
     */
    public T convertByInherit(S source) {
        if (source == null) {
            return null;
        }
        convert(source, (T) this);
        return (T) this;
    }


    /**
     * 传入资源对象,返回目标对象
     * 利用反射,创建目标对象
     * <p>可以继承,可以不继承</p>
     * <p>注: 只能通过CustomConverter,来调用</p>
     * <p>
     * UserDTO dto = new CustomConverter<User, UserDTO>(UserDTO.class).convert(user);
     * </p>
     *
     * @param source 资源对象
     * @return 目标对象
     */
    public T convert(S source) {
        if (source == null) {
            return null;
        }
        convert(source, t);
        return t;
    }

}
