package a_lambda.z_zone;

import java.util.EnumMap;

public class StrategyExample {
    interface Sound {
        void make();
    }

    enum WeaponType{
        SWORD,
        AXE,
        RIFLE,
        MACHINE_GUN,
        BOMB,
        MAGIC_WAND
    }


    public static void main(String[] args) {
        EnumMap<WeaponType, Sound> weaponSoundStrategies = new EnumMap<>(WeaponType.class);
        weaponSoundStrategies.put(WeaponType.SWORD, () -> System.out.println("хыщ!"));
        weaponSoundStrategies.put(WeaponType.AXE, () -> System.out.println("тюк!"));
        weaponSoundStrategies.put(WeaponType.RIFLE, () -> System.out.println("бдыщ!"));
        weaponSoundStrategies.put(WeaponType.MACHINE_GUN, () -> System.out.println("тра-та-та-та-та-та!"));
        weaponSoundStrategies.put(WeaponType.BOMB, () -> System.out.println("бум!"));
        weaponSoundStrategies.put(WeaponType.MAGIC_WAND, () -> System.out.println("вжух!"));

    }
}
